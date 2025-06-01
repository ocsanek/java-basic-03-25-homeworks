package ru.otus.java.basic.homeworks.homework18;

import java.util.*;

public class PersonDataBase {
    private Map<Long, Person> personMap;

    public PersonDataBase() {
        this.personMap = new HashMap<>();
    }
    private static final Set<Position> managers = new HashSet<>(Arrays.asList(
            Position.MANAGER,
            Position.DIRECTOR,
            Position.BRANCH_DIRECTOR,
            Position.SENIOR_MANAGER);

    public Person findById(Long id) {
        return personMap.get(id);
    }

    public void add(Person person) {
        personMap.put(person.id, person);
    }

    public boolean isManager(Person person) {
        return managers.contains(person.position);
    }

    public boolean isEmployee(Long id) {
        Person person = personMap.get(id);
        if (person == null) return false;

        return !managers.contains(person.position);

    }
}
