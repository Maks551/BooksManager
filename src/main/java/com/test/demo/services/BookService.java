package com.test.demo.services;

import com.test.demo.model.Book;

import java.util.List;

public interface BookService {
    Book getById(Integer id);
    void save(String title, String author, String description,
              String isbn, Integer printYear);
    void update(Integer id, String title, String author,
                String description, String isbn, Integer printYear);
    void read(Integer id);
    void delete(Integer id);
    List<Book> findAll();
    List<Book> getAllForLimit(int offset, int limit);
    Integer getCount();
}
