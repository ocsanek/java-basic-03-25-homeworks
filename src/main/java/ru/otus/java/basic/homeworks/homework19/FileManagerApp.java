package ru.otus.java.basic.homeworks.homework19;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileManagerApp {


    public static void main(String[] args) {
        File dir = new File("files");
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Папка files не найдена в корне проекта.");
            return;
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("В папке files нет текстовых файлов.");
            return;
        }

        System.out.println("Список текстовых файлов в папке files:");
        for (File file : files) {
            System.out.println(file.getName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите имя файла (без расширения .txt): ");
        String fileName = scanner.nextLine().trim() + ".txt";

        File selectedFile = new File(dir, fileName);
        if (!selectedFile.exists()) {
            System.out.println("Файл не найден!");
            return;
        }

        System.out.println("\nСодержимое файла " + selectedFile.getName() + ":");
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(selectedFile))) {
            int n;
            while ((n = in.read()) != -1) {
                System.out.print((char) n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("\n\nВведите строку для добавления в файл: ");
        String userLine = scanner.nextLine();


        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(selectedFile, true))) {
            userLine += System.lineSeparator();
            byte[] data = userLine.getBytes(StandardCharsets.UTF_8);
            out.write(data);
            out.flush();
            System.out.println("Строка успешно добавлена в файл!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}