package ru.otus.java.basic.homeworks.homework18;

import java.util.HashMap;
import java.util.Map;

public class PersonDataBase {
    private Map<Long, Person> personMap;

    public PersonDataBase() {
        this.personMap = new HashMap<>();
    }

    public Person findById(Long id) {
        return personMap.get(id);
    }

    public void add(Person person) {
        personMap.put(person.id, person);
    }

    public boolean isManager(Person person) {
        Position p = person.position;
        return p == Position.MANAGER || p == Position.DIRECTOR ||
                p == Position.BRANCH_DIRECTOR || p == Position.SENIOR_MANAGER;
    }

    public boolean isEmployee(Long id) {
        Person person = personMap.get(id);
        if (person == null) return false;

        Position p = person.position;
        return p != Position.MANAGER &&
                p != Position.DIRECTOR &&
                p != Position.BRANCH_DIRECTOR &&
                p != Position.SENIOR_MANAGER;

    }
}
