package ru.otus.chat.client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
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
                            if (message.equals("/exitok")) { break; }
                            else if (message.startsWith("/authok ")) {
                                System.out.println("Успешный вход как " + message.split(" ")[1]);
                            } else if (message.startsWith("/regok ")) {
                                System.out.println("Регистрация ок, вошли как " + message.split(" ")[1]);
                            } else if (message.startsWith("/error ")) {
                                System.out.println("[ОШИБКА] " + message);
                            } else if (message.startsWith("/info ")) {
                                System.out.println("[ИНФО] " + message);
                            } else if (message.startsWith("/activelistok ")) {
                                System.out.println("Активны: " + message.substring("/activelistok ".length()));
                            } else if (message.startsWith("/filepmfrom ")) {
                                // /filepmfrom <from> <filename> <base64>
                                String[] t = message.split(" ", 4);
                                if (t.length==4){
                                    String from = t[1];
                                    String filename = t[2];
                                    String b64 = t[3];
                                    try {
                                        byte[] data = Base64.getDecoder().decode(b64);
                                        Path outPath = Path.of("recv_" + filename);
                                        Files.write(outPath, data);
                                        System.out.println("[ФАЙЛ] Получен от " + from + ": " + filename + " (" + data.length + " байт). Сохранён в " + outPath.toAbsolutePath());
                                    } catch (Exception e) {
                                        System.out.println("[ФАЙЛ] Ошибка сохранения: " + e.getMessage());
                                    }
                                }
                            }
                        } else {
                            System.out.println(message);
                        }
                    }
                } catch (IOException e) {
                    // соединение закрыто
                } finally {
                    disconnect();
                }
            }, "reader").start();

            while (true) {
                String line = scanner.nextLine();

                // перехватываем отправку маленьких файлов (PM)
                if (line.startsWith("/sendfile ")) {
                    // формат: /sendfile <username> <path>
                    String[] t = line.split(" ", 3);
                    if (t.length != 3) {
                        System.out.println("Формат: /sendfile <username> <path>");
                        continue;
                    }
                    String to = t[1];
                    Path p = Path.of(t[2]);
                    if (!Files.exists(p) || !Files.isRegularFile(p)) {
                        System.out.println("Файл не найден: " + p);
                        continue;
                    }
                    byte[] data = Files.readAllBytes(p);
                    int max = 256 * 1024; // 256 КБ
                    if (data.length > max) {
                        System.out.println("Файл слишком большой (max 256KB)");
                        continue;
                    }
                    String b64 = Base64.getEncoder().encodeToString(data);
                    String filename = p.getFileName().toString();
                    out.writeUTF("/filepm " + to + " " + filename + " " + b64);
                    continue;
                }

                out.writeUTF(line);
                if (line.equals("/exit")) break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try { if (in != null) in.close(); } catch (IOException ignored) {}
        try { if (out != null) out.close(); } catch (IOException ignored) {}
        try { if (socket != null) socket.close(); } catch (IOException ignored) {}
    }
}
