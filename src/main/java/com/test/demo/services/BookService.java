package com.test.demo.services;

import com.test.demo.model.Book;

import java.util.List;

public interface BookService {
    Book getBookById(Integer id);
    void saveBook(String title, String author, String description,
                  String isbn, Integer printYear);
    void updateBook(Integer id, String title, String description,
                    String isbn, Integer printYear);
    void readBook(Integer id);
    void deleteBook(Integer id);
    List<Book> findAll();
    List<Book> getBooksByPage(int pageId,int total);
}
