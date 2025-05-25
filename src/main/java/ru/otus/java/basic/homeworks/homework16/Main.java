package ru.otus.java.basic.homeworks.homework16;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = ListMethods.createRangeList(1, 10);
        System.out.println("Список диапазонов: " + numbers);
        System.out.println("Больше пяти: " + ListMethods.sumGreaterThanFive(numbers));


        ListMethods.fillListWithNumber(7, numbers);
        System.out.println("Заполнено 7: " + numbers);

        ListMethods.increaseListNumber(3, numbers);
        System.out.println("Увеличено на 3: " + numbers);


        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Иван", 25));
        employees.add(new Employee("Мария", 31));
        employees.add(new Employee("Петр", 18));
        employees.add(new Employee("Иоанн", 74));
        employees.add(new Employee("Ольга", 51));
        employees.add(new Employee("Вероника", 21));
        employees.add(new Employee("Дмитрий", 19));
        employees.add(new Employee("Евгений", 18));

        System.out.println("Имена сотрудников: "+ ListMethods.getEmployeeNames(employees));
        System.out.println("Старше 21: "+ ListMethods.getEmployeesByMinAge(employees, 21));
        System.out.println("Старше минимального среднего возраста: "+ ListMethods.isAverageAgeGreaterThan(employees, 25));
        System.out.println("Младший сотрудник: "+ ListMethods.getYoungestEmployee(employees));
    }
}
