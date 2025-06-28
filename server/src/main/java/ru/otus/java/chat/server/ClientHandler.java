package ru.otus.java.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private Role role;

    private String username;
    private boolean authenticated;

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());


        new Thread(() -> {
            try {
                //Цикл аутентификации
                while (true) {
                    sendMsg("Перед работой с чатом необходимо выполнить аутентификацию '/auth login password'" +
                            " или зарегистрироваться '/reg login password username'");
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMsg("/exitok");
                            break;
                        }
                        // /auth login password
                        if (message.startsWith("/auth ")) {
                            String[] token = message.split(" ");
                            if (token.length != 3) {
                                sendMsg("Неверный формат команды /auth");
                                continue;
                            }
                            if (server.getAuthenticatedProvider()
                                    .authenticate(this, token[1], token[2])) {
                                authenticated = true;
                                break;
                            }
                        }
                        // /reg login password username
                        if (message.startsWith("/reg ")) {
                            String[] token = message.split(" ");
                            if (token.length != 4) {
                                sendMsg("Неверный формат команды /reg");
                                continue;
                            }
                            if (server.getAuthenticatedProvider()
                                    .registration(this, token[1], token[2], token[3])) {
                                authenticated = true;
                                break;
                            }
                        }
                    }
                }
                //Цикл работы
                while (authenticated) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMsg("/exitok");
                            break;
                        }
                        if (message.startsWith("/kick ")) {
                            if (role != Role.ADMIN) {
                                sendMsg("Недостаточно прав.");
                                continue;
                            }
                            String[] tokens = message.split(" ");
                            if (tokens.length != 2) {
                                sendMsg("Формат команды: /kick username");
                                continue;
                            }
                            String targetUsername = tokens[1];
                            if (targetUsername.equals(this.username)) {
                                sendMsg("Нельзя кикнуть самого себя");
                                continue;
                            }
                            boolean result = server.kickClientByUsername(targetUsername);
                            if (result) {
                                sendMsg("Пользователь " + targetUsername + " кикнут.");
                            } else {
                                sendMsg("Пользователь " + targetUsername + " не найден.");
                            }
                        }
                    } else {
                        server.broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                disconnect();
            }
        }).start();
    }

    public void sendMsg(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
