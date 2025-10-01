package ru.otus.java.basic.homeworks.homework33.server.application;

import ru.otus.java.basic.homeworks.homework33.server.application.dtos.Item;

import java.math.BigDecimal;
import java.util.*;

public class ItemsRepository {
    private final List<Item> items;

    public ItemsRepository() {
        this.items = new ArrayList<>(Arrays.asList(
            new Item(1L, "Bread", BigDecimal.valueOf(35), new int[]{1}),
            new Item(2L, "Milk", BigDecimal.valueOf(80), new int[]{1, 5}),
            new Item(3L, "Cheese", BigDecimal.valueOf(400), new int[]{1})
        ));
    }

    public List<Item> getAll() { return Collections.unmodifiableList(items); }

    public Item getById(Long id) {
        return items.stream().filter(i -> i.getId().equals(id)).findFirst().orElseThrow();
    }

    public Item createNew(Item item) {
        Long newId = items.stream().mapToLong(Item::getId).max().orElse(0L) + 1;
        item.setId(newId);
        items.add(item);
        return item;
    }

    // для DELETE /items/{id}
    public boolean deleteById(Long id) {
        return items.removeIf(i -> i.getId().equals(id));
    }
}
