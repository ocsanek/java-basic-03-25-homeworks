package ru.otus.java.basic.homeworks.homework20;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        String serverAdress = "localhost";
        int port = 8091;

        try (Socket socket = new Socket(serverAdress, port)) {
            System.out.println("Подключено к серверу: " + serverAdress + ":" + port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            String operations = reader.readLine();
            System.out.println(operations);

            System.out.println("Введите выражение (например, 5 + 2): ");
            String request = scanner.nextLine();
            writer.write(request);
            writer.newLine();
            writer.flush();

            String response = reader.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}