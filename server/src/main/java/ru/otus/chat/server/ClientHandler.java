package ru.otus.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class ClientHandler {
    private final Socket socket;
    private final Server server;
    private final DataInputStream in;
    private final DataOutputStream out;

    private String username;
    private String role = "user";
    private volatile boolean authenticated;
    private final AtomicLong lastActivity = new AtomicLong(System.currentTimeMillis());

    // комнаты
    private String currentRoom = "general";

    // антиспам
    private final Deque<Long> sentTs = new ArrayDeque<>();
    private static final int SPAM_N = 10;     // максимум сообщений
    private static final int SPAM_T_SEC = 30; // окно

    // фильтр плохих слов
    private static final Set<String> BAD = Set.of("дурак", "идиот", "редиска");

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        new Thread(this::loop, "client-"+socket.getPort()).start();
    }

    private void loop(){
        try {
            // цикл аутентификации
            while (!authenticated) {
                sendMsg("Перед началом: /auth login pass или /register login pass username");
                String message = in.readUTF();
                touch();
                if (message.startsWith("/")) {
                    if (message.equals("/exit")) { sendMsg("/exitok"); return; }
                    if (message.startsWith("/auth ")) {
                        String[] t = message.split(" ");
                        if (t.length==3 && server.getAuthenticatedProvider().authenticate(this, t[1], t[2])) {
                            authenticated = true;
                            server.joinRoom(this, currentRoom);
                            server.broadcastSystem("Клиент " + username + " вошёл в чат");
                            // история текущей комнаты
                            for (String m : server.getMessagesDao().lastRoomMessages(currentRoom, 50)) sendMsg(m);
                            break;
                        }
                    }
                    if (message.startsWith("/register ")) {
                        String[] t = message.split(" ");
                        if (t.length==4 && server.getAuthenticatedProvider().registration(this, t[1], t[2], t[3])) {
                            authenticated = true;
                            server.joinRoom(this, currentRoom);
                            server.broadcastSystem("Клиент " + username + " зарегистрировался и вошёл в чат");
                            break;
                        }
                    }
                }
            }
            // рабочий цикл
            while (authenticated) {
                String message = in.readUTF();
                touch();
                if (message.startsWith("/")) {
                    if (message.equals("/exit")) { sendMsg("/exitok"); break; }

                    else if (message.startsWith("/w ")) {
                        String[] t = message.split(" ",3);
                        if (t.length<3){ sendMsg("/error BAD_PM_FORMAT use:/w user text"); continue; }
                        server.sendPrivate(username, t[1], filter(t[2]));
                    }

                    else if (message.equals("/activelist")) {
                        sendMsg("/activelistok " + server.activeList());
                    }

                    else if (message.startsWith("/changenick ")) {
                        String[] t = message.split(" ");
                        if (t.length!=2){ sendMsg("/error BAD_NICK_FORMAT"); continue; }
                        String newU = t[1];
                        if (server.isUsernameBusy(newU)) { sendMsg("/error USERNAME_BUSY"); continue; }
                        server.getUsersDao().changeNick(username, newU);
                        server.broadcastSystem(username + " → " + newU);
                        this.username = newU;
                        sendMsg("/info nick_ok " + newU);
                    }

                    else if (message.startsWith("/ban ")) {
                        if (!"admin".equals(role)) { sendMsg("/error NO_RIGHTS"); continue; }
                        String[] t = message.split(" ");
                        if (t.length!=3){ sendMsg("/error BAN_FORMAT use:/ban user <minutes|perm>"); continue; }
                        String target = t[1]; String m = t[2];
                        boolean perm = "perm".equalsIgnoreCase(m);
                        int minutes = perm? 0 : Integer.parseInt(m);
                        server.getUsersDao().ban(target, minutes, perm);
                        var ch = server.findByUsername(target);
                        if (ch!=null){ ch.sendMsg("/info You_are_banned"); ch.disconnect(); }
                        server.broadcastSystem("Пользователь " + target + " забанен (" + (perm? "perm" : (minutes+" min")) + ")");
                    }

                    else if (message.equals("/shutdown")) {
                        if (!"admin".equals(role)) { sendMsg("/error NO_RIGHTS"); continue; }
                        server.shutdown();
                    }

                    //Комнаты

                    else if (message.equals("/rooms")) {
                        sendMsg("/info rooms " + String.join(",", server.listRooms()));
                    }

                    else if (message.startsWith("/mkroom ")) {
                        String[] t = message.split(" ");
                        if (t.length<2 || t.length>3){ sendMsg("/error MKROOM_FORMAT use:/mkroom name [pass]"); continue; }
                        String rn = t[1]; String pass = t.length==3? t[2]: null;
                        if (!server.getRoomsDao().canOwnMore(username)){ sendMsg("/error ROOM_LIMIT 5"); continue; }
                        if (server.getRoomsDao().exists(rn)){ sendMsg("/error ROOM_EXISTS"); continue; }
                        server.getRoomsDao().create(rn, pass, username);
                        sendMsg("/info mkroom_ok " + rn);
                    }

                    else if (message.startsWith("/enter ")) {
                        String[] t = message.split(" ");
                        if (t.length<2 || t.length>3){ sendMsg("/error ENTER_FORMAT use:/enter name [pass]"); continue; }
                        String rn = t[1]; String pass = t.length==3? t[2]: null;
                        if (!server.getRoomsDao().exists(rn)){ sendMsg("/error NO_SUCH_ROOM"); continue; }
                        if (!server.getRoomsDao().checkPass(rn, pass)){ sendMsg("/error ROOM_PASS"); continue; }
                        server.leaveRoom(this, currentRoom);
                        currentRoom = rn;
                        server.joinRoom(this, currentRoom);
                        sendMsg("/info enter_ok " + currentRoom);
                        for (String m : server.getMessagesDao().lastRoomMessages(currentRoom, 50)) sendMsg(m);
                    }

                    else if (message.equals("/leave")) {
                        if (!"general".equals(currentRoom)){
                            server.leaveRoom(this, currentRoom);
                            currentRoom = "general";
                            server.joinRoom(this, currentRoom);
                            sendMsg("/info enter_ok general");
                        }
                    }

                    else if (message.equals("/whereami")) {
                        sendMsg("/info room " + currentRoom);
                    }

                    //Последняя активность

                    else if (message.startsWith("/last_activity ")) {
                        String[] t = message.split(" ");
                        if (t.length!=2){ sendMsg("/error LAST_ACTIVITY_FORMAT use:/last_activity user"); continue; }
                        var ts = server.getUsersDao().lastActivity(t[1]);
                        sendMsg("/last_activity_ok " + t[1] + " " + (ts==null? "unknown" : ts.toString()));
                    }

                    //Рейтинги
                    else if (message.startsWith("/like ")) {
                        // Здесь для краткости делаем просто запись в таблицу.
                        String[] t = message.split(" ");
                        if (t.length!=3){ sendMsg("/error LIKE_FORMAT use:/like user [+1|-1]"); continue; }
                        String who = t[1];
                        if (who.equals(username)){ sendMsg("/error LIKE_SELF"); continue; }
                        int d;
                        try { d = Integer.parseInt(t[2]); } catch (Exception e){ sendMsg("/error LIKE_DELTA"); continue; }
                        try (var c = server.getUsersDao().getConnection();
                             var ps = c.prepareStatement("INSERT INTO ratings(rater,rated,delta) VALUES(?,?,?)")){
                            ps.setString(1, username);
                            ps.setString(2, who);
                            ps.setInt(3, d);
                            ps.executeUpdate();
                            sendMsg("/info like_ok " + who + " " + d);
                        } catch (Exception e){ sendMsg("/error LIKE_FAIL " + e.getMessage()); }
                    }

                    //Передача небольших файлов (PM, base64)
                    else if (message.startsWith("/filepm ")) {
                        // формат: /filepm <to> <filename> <base64>
                        String[] t = message.split(" ", 4);
                        if (t.length!=4){ sendMsg("/error FILEPM_FORMAT use:/sendfile user path"); continue; }
                        String to = t[1];
                        var rcpt = server.findByUsername(to);
                        if (rcpt==null){ sendMsg("/error NO_SUCH_USER " + to); continue; }
                        // просто ретрансляция получателю
                        rcpt.sendMsg("/filepmfrom " + username + " " + t[2] + " " + t[3]);
                        sendMsg("/info file_sent " + to + " " + t[2]);
                    }

                    else {
                        sendMsg("/error UNKNOWN_CMD");
                    }
                } else {
                    if (isSpam()) { sendMsg("/error SPAM_LIMIT " + SPAM_N + " per " + SPAM_T_SEC + "s"); continue; }
                    String filtered = filter(message);
                    server.broadcastToRoom(currentRoom, username, filtered);
                }
            }
        } catch (IOException ignored) {
        } finally {
            disconnect();
        }
    }

    private boolean isSpam(){
        long now = System.currentTimeMillis();
        sentTs.addLast(now);
        while (!sentTs.isEmpty() && now - sentTs.peekFirst() > SPAM_T_SEC*1000L) sentTs.removeFirst();
        return sentTs.size() > SPAM_N;
    }

    private String filter(String s){
        String out = s;
        for (String w : BAD){
            out = out.replaceAll("(?i)\\b" + java.util.regex.Pattern.quote(w) + "\\b", "***");
        }
        return out;
    }

    public void sendMsg(String message) {
        try { out.writeUTF(message); } catch (IOException ignored) {}
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public void setRole(String role){ this.role = role; }
    public String getCurrentRoomSafe(){ return currentRoom; }

    public void disconnect() {
        server.unsubscribe(this);
        try { in.close(); } catch (Exception ignored) {}
        try { out.close(); } catch (Exception ignored) {}
        try { socket.close(); } catch (Exception ignored) {}
    }

    public void touch(){ lastActivity.set(System.currentTimeMillis()); server.getUsersDao().touchActivity(username); }
    public long getLastActivityMs(){ return lastActivity.get(); }
}