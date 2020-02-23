package com.sava.db;

import java.util.List;

public interface Dao<T> {
    /**
     * Get a record from a table by its ID
     *
     * @param id
     *         a PK of a record
     *
     * @return an object representing the record
     */
    T getById(int id);

    /**
     * Get all table records
     *
     * @return a list of record objects
     */
    List<T> getAll();

    /**
     * Create a new DB record
     *
     * @param newObj
     *         an entity representing the record
     *
     * @return created object
     */
    T create(T newObj);

    /**
     * Update a new DB record if it exists. Otherwise, create a new record
     *
     * @param updObj
     *         an entity representing the record
     *
     * @return updated object
     */
    T update(T updObj);
}
