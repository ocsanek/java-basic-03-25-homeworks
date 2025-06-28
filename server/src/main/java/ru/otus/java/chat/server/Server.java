package ru.otus.java.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private int port;
    private List<ClientHandler> clients;
    private AuthenticatedProvider authenticatedProvider;

    public Server(int port) {
        this.port = port;
        clients = new CopyOnWriteArrayList<>();
        authenticatedProvider = new InMemoryAuthenticatedProvider(this);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            authenticatedProvider.initialize();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился " + socket.getPort());
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        broadcastMessage("Клиент " + clientHandler.getUsername() + " вышел из чата");
        System.out.println("Клиент " + clientHandler.getUsername() + " вышел из чата");
        clients.remove(clientHandler);
    }

    public void broadcastMessage(String message) {
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }

    public boolean kickClientByUsername(String username) {
        for (ClientHandler c : clients) {
            if (c.getUsername().equals(username)) {
                c.sendMsg("Вы были отключены от сервера.");
                c.disconnect();
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameBusy(String username) {
        for (ClientHandler c : clients) {
            if (c.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public AuthenticatedProvider getAuthenticatedProvider() {
        return authenticatedProvider;
    }
}
