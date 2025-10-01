package ru.otus.chat.server;

import ru.otus.chat.server.db.Db;
import ru.otus.chat.server.db.UsersDao;
import ru.otus.chat.server.db.RoomsDao;
import ru.otus.chat.server.db.MessagesDao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private final int port;
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    private final Db db = new Db(System.getProperty("db.url", "jdbc:h2:./chat-db;AUTO_SERVER=TRUE"));
    private final UsersDao usersDao = new UsersDao(db);
    private final RoomsDao roomsDao = new RoomsDao(db);
    private final MessagesDao messagesDao = new MessagesDao(db);
    private AuthenticatedProvider authenticatedProvider;
    private volatile boolean running = true;

    // room -> members
    private final Map<String, Set<ClientHandler>> roomMembers = new ConcurrentHashMap<>();

    public Server(int port) {
        this.port = port;
        roomMembers.put("general", ConcurrentHashMap.newKeySet());
    }

    public void start() {
        db.init();
        authenticatedProvider = new DbAuthenticatedProvider(this, usersDao);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            authenticatedProvider.initialize();
            new Thread(this::idleKicker, "idle-kicker").start();
            new Thread(this::roomsCleaner, "rooms-cleaner").start();
            while (running) {
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился " + socket.getRemoteSocketAddress());
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown(){
        running = false;
        broadcastSystem("Сервер выключается");
        System.exit(0);
    }

    public void subscribe(ClientHandler clientHandler) { clients.add(clientHandler); }

    public void unsubscribe(ClientHandler clientHandler) {
        leaveRoom(clientHandler, clientHandler.getCurrentRoomSafe());
        if (clientHandler.getUsername()!=null) {
            broadcastSystem("Клиент " + clientHandler.getUsername() + " вышел из чата");
        }
        clients.remove(clientHandler);
    }

    public void broadcastMessage(String from, String body){
        String ts = LocalDateTime.now().toString();
        String msg = "["+ts+"] " + from + ": " + body;
        for (ClientHandler c : clients) c.sendMsg(msg);
    }

    public void broadcastSystem(String body){
        String ts = LocalDateTime.now().toString();
        for (ClientHandler c : clients) c.sendMsg("[" + ts + "] [SYSTEM] " + body);
    }

    public void sendPrivate(String from, String to, String body){
        ClientHandler rcpt = findByUsername(to);
        String ts = LocalDateTime.now().toString();
        if (rcpt==null){
            var me = findByUsername(from);
            if (me!=null) me.sendMsg("/error NO_SUCH_USER " + to);
            return;
        }
        rcpt.sendMsg("[" + ts + "] (PM) " + from + " → you: " + body);
        var me = findByUsername(from);
        if (me!=null) me.sendMsg("[" + ts + "] (PM) you → " + to + ": " + body);
    }

    public ClientHandler findByUsername(String u){
        for (ClientHandler c: clients) if (u.equals(c.getUsername())) return c;
        return null;
    }

    public boolean isUsernameBusy(String username){
        for (ClientHandler c: clients) if (username!=null && username.equals(c.getUsername())) return true;
        return false;
    }

    public String activeList(){
        return clients.stream().map(ClientHandler::getUsername).sorted().reduce((a,b)->a+","+b).orElse("");
    }

    private void idleKicker(){
        while (true){
            long now = System.currentTimeMillis();
            for (ClientHandler c: new ArrayList<>(clients)){
                if (now - c.getLastActivityMs() > 20*60*1000L){
                    c.sendMsg("/info AFK>20min. Disconnecting.");
                    c.disconnect();
                }
            }
            try { Thread.sleep(15_000);} catch (InterruptedException ignored) {}
        }
    }

    private void roomsCleaner(){
        while (true){
            try {
                roomsDao.cleanupOldEmptyRooms(roomMembers.keySet());
                Thread.sleep(24*3600_000L);
            } catch (Exception ignored) { }
        }
    }

    /* ----- Комнаты ----- */

    public void joinRoom(ClientHandler ch, String room){
        roomMembers.computeIfAbsent(room, k->ConcurrentHashMap.newKeySet()).add(ch);
    }

    public void leaveRoom(ClientHandler ch, String room){
        if (room==null) return;
        var set = roomMembers.get(room);
        if (set!=null) {
            set.remove(ch);
            if (set.isEmpty() && !room.equals("general")) {
                roomMembers.remove(room);
            }
        }
    }

    public void broadcastToRoom(String room, String from, String body){
        String ts = LocalDateTime.now().toString();
        String msg = "["+ts+"] " + from + ": " + body;
        var set = roomMembers.get(room);
        if (set!=null) set.forEach(c -> c.sendMsg(msg));
        messagesDao.savePublic(from, room, body);
        roomsDao.touch(room);
    }

    public List<String> listRooms(){ return roomsDao.list(); }

    /* ----- Геттеры ----- */
    public UsersDao getUsersDao(){ return usersDao; }
    public RoomsDao getRoomsDao(){ return roomsDao; }
    public MessagesDao getMessagesDao(){ return messagesDao; }
    public AuthenticatedProvider getAuthenticatedProvider() { return authenticatedProvider; }
}
