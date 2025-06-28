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
    private String username;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        this.username = "user" + socket.getPort();
        sendMsg("Вы подключились под ником: " + username);

        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        handleCommand(message);
                    } else {
                        server.broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Клиент отключился: " + username);
            } finally {
                disconnect();
            }
        }).start();
    }

    private void handleCommand(String message) {
        try {
            if (message.equals("/exit")) {
                sendMsg("/exitok");
                disconnect();
            }
            else if (message.startsWith("/w ")) {
                String[] tokens = message.split("\\s+", 3);
                if (tokens.length < 3) {
                    sendMsg("Неверный формат. Используй: /w username сообщение");
                    return;
                }
                String recipientName = tokens[1];
                String privateMessage = tokens[2];
                ClientHandler recipient = server.getClientByUsername(recipientName);
                if (recipient != null) {
                    recipient.sendMsg("(личное от " + username + "): " + privateMessage);
                    sendMsg("(личное для " + recipientName + "): " + privateMessage);
                } else {
                    sendMsg("Пользователь с ником " + recipientName + " не найден.");
                }
            }
            else if (message.startsWith("/setname")) {
                String[] tokens = message.split("\\s+", 2);
                if (tokens.length < 2 || tokens[1].trim().isEmpty()) {
                    sendMsg("Ник не может быть пустым.");
                } else {
                    String newUsername = tokens[1].trim();
                    if (server.getClientByUsername(newUsername) == null) {
                        String oldUsername = username;
                        username = newUsername;
                        sendMsg("Ваш ник успешно установлен: " + username);
                        server.broadcastMessage("Пользователь " + oldUsername + " теперь известен как " + username);
                    } else {
                        sendMsg("Такой ник уже занят. Попробуйте другой.");
                    }
                }
            }
            else {
                sendMsg("Неизвестная команда.");
            }
        } catch (Exception e) {
            sendMsg("Ошибка обработки команды.");
        }
    }

    public void sendMsg(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println("Ошибка отправки сообщения клиенту " + username);
        }
    }

    public String getUsername() {
        return username;
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            if (in != null) in.close();
        } catch (IOException e) {}
        try {
            if (out != null) out.close();
        } catch (IOException e) {}
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {}
    }
}
