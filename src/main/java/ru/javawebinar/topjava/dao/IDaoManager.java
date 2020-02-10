package ru.javawebinar.topjava.dao;

import java.util.List;

public interface IDaoManager<T> {
    void add(T obj);

    T get(int key);

    void update(T Create);

    void delete(int key);

    List<T> getAll();
}
