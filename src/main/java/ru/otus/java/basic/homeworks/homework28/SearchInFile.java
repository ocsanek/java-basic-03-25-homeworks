package ru.otus.java.basic.homeworks.homework28;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SearchInFile {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод имени файла
        System.out.print("Введите имя файла: ");
        String fileName = scanner.nextLine();

        // Ввод искомой строки
        System.out.print("Введите последовательность символов для поиска: ");
        String searchString = scanner.nextLine();

        try {
            int count = countOccurrences(fileName, searchString);
            System.out.println("Последовательность \"" + searchString + "\" найдена " + count + " раз(а).");
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public static int countOccurrences(String fileName, String searchString) throws IOException {
        int count = 0;
        try (
            BufferedReader reader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                count += countMatchesInLine(line, searchString);
            }
        }
        return count;
    }

    // Метод для подсчета количества вхождений подстроки в строку
    private static int countMatchesInLine(String line, String search) {
        int count = 0;
        int index = 0;
        while ((index = line.indexOf(search, index)) != -1) {
            count++;
            index += search.length(); // переходим за найденную подстроку
        }
        return count;
    }
}

