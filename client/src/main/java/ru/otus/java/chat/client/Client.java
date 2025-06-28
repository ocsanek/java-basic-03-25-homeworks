package ru.otus.java.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    public Client() {
        Scanner scanner = new Scanner(System.in);
        try {
            this.socket = new Socket("localhost", 8189);
            this.out = new DataOutputStream(socket.getOutputStream());
            this.in = new DataInputStream(socket.getInputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readUTF();
                        if (message.startsWith("/")) {
                            if (message.equals("/exitok")) {
                                break;
                            }
                            if (message.startsWith("/authok ")) {
                                System.out.println("Удалось успешно войти в чат " +
                                        "под именем пользователя "+ message.split(" ")[1]);
                            }
                            if (message.startsWith("/regok ")) {
                                System.out.println("Удалось успешно зарегистроваться и войти в чат " +
                                        "под именем пользователя "+ message.split(" ")[1]);
                            }
                        } else {
                            System.out.println(message);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    disconnect();
                }
            }).start();

            while (true) {
                String message = scanner.nextLine();
                out.writeUTF(message);
                if (message.equals("/exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
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