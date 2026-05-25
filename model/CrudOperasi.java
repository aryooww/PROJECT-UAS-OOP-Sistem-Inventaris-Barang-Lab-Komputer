package model;

import java.util.List;

public interface CrudOperasi<T> {
    void create(T obj); // Create
    List<T> readAll(); // Read all
    T readById(String id); // Read by ID
    void update(T obj); // Update
    void delete(String id); // Delete
}