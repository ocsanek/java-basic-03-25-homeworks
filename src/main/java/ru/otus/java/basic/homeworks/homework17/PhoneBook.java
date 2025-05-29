package ru.otus.java.basic.homeworks.homework17;

import java.util.*;

public class PhoneBook {
    private Map<String, Set<String>> phoneBook;

    public PhoneBook() {
        phoneBook = new HashMap<>();
    }

    public void add(String name, String phoneNumber){
        if (!phoneBook.containsKey(name)) {
            phoneBook.put(name, new HashSet<>());
        }
        phoneBook.get(name).add(phoneNumber);
    }

    public Set<String> find(String name){
        return phoneBook.getOrDefault(name, Collections.emptySet());
    }

    public boolean containsPhoneNumber(String phoneNumber){
        for (Set<String> numbers : phoneBook.values()) {
            if (numbers.contains(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public void printPhoneBook() {
        for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
