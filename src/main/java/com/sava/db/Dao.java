package com.sava.db;

import java.util.List;

public interface Dao<T> {
    T getById(int id);

    List<T> getAll();

    T create(T newObj);

    T update();
}
