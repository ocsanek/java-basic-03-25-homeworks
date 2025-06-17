package ru.otus.java.basic.homeworks.homework20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {
    public static void main(String[] args) {
        int port = 8091;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Сервер запущен на порту: " + port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Подключился клиент: " + clientSocket.getInetAddress());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    writer.write("Доступные операции: +, -, *, /");
                    writer.newLine();
                    writer.flush();

                    String request = reader.readLine();
                    System.out.println("Запрос от клиента: " + request);

                    String[] parts = request.split(" ");
                    if (parts.length != 3) {
                        writer.write("Ошибка! Неверный формат запроса!");
                        writer.newLine();
                        writer.flush();
                    } else {
                        double num1 = Double.parseDouble(parts[0]);
                        String operation = parts[1];
                        double num2 = Double.parseDouble(parts[2]);
                        double result = 0;

                        switch (operation) {
                            case "+" -> result = num1 + num2;
                            case "-" -> result = num1 - num2;
                            case "*" -> result = num1 * num2;
                            case "/" -> {
                                if (num2 != 0) result = num1 / num2;
                            else {
                                writer.write("Ошибка! Деление на ноль.");
                                writer.newLine();
                                writer.flush();
                                continue;
                                }
                            }
                            default -> {
                                writer.write("Ошибка! Неизвестная операция!");
                                writer.newLine();
                                writer.flush();
                                continue;
                            }
                        }
                        writer.write("Результат: " + result);
                        writer.newLine();
                        writer.flush();
                    }
                    clientSocket.close();
                }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }
}