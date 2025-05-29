package ru.otus.java.basic.homeworks.homework16;

import java.util.ArrayList;
import java.util.List;

public class ListMethods {
    public static ArrayList<Integer> createRangeList(int min, int max) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            list.add(i);
        }
        return list;
    }

    public static int sumGreaterThanFive(List<Integer> list) {
        int sum = 0;
        for (int num : list) {
            if (num > 5) {
                sum += num;
            }
        }
        return sum;
    }

    public static void fillListWithNumber(int number, List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, number);
        }
    }

    public static void increaseListNumber(int number, List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) + number);
        }
    }

    public static List<String> getEmployeeNames(List<Employee> employees) {
        List<String> names = new ArrayList<>();
        for (Employee emp : employees) {
            names.add(emp.getName());
        }
        return names;
    }

    public static List<String> getEmployeesByMinAge(List<Employee> employees, int minAge) {
        List<String> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getAge() >= minAge) {
                result.add(emp.getName());
            }
        }
        return result;
    }

    public static boolean isAverageAgeGreaterThan(List<Employee> employees, double minAverageAge) {
        if (employees.isEmpty()) {
            return false;
        }
        double sum = 0;
        for (Employee emp : employees) {
            sum += emp.getAge();
        }
        double average = sum / employees.size();
        return average > minAverageAge;
    }

    public static Employee getYoungestEmployee(List<Employee> employees) {
        if (employees.isEmpty()) {
            return null;
        }
        Employee youngest = employees.get(0);
        for (Employee emp : employees) {
            if (emp.getAge() < youngest.getAge()) {
                youngest = emp;
            }
        }
        return youngest;
    }



}
