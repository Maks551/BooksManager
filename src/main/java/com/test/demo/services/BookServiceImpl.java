package com.test.demo.services;

import com.test.demo.model.Book;
import com.test.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository repository){
        this.repository = repository;
    }

    @Cacheable(value = "book", key = "#id")
    public Book getById(Integer id) {
        return repository.getOne(id);
    }

    @CacheEvict(value = "book", allEntries = true)
    @Override
    public void save(String title, String author, String description,
                     String isbn, Integer printYear){
        repository.save(new Book(title, author ,description, isbn, printYear));
    }

    @CacheEvict(value = "book", allEntries = true)
    @Override
    public void update(Integer id, String title, String description, String isbn, Integer printYear) {
        Book book = repository.getOne(id);
        book.setTitle(title);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setPrintYear(printYear);
        book.setReadAlready(false);
        repository.save(book);
    }

    @CacheEvict(value = "book", allEntries = true)
    @Override
    public void read(Integer id) {
        Book book = repository.getOne(id);
        if (!book.isReadAlready()) {
            book.setReadAlready(true);
            repository.save(book);
        }
    }

    @CacheEvict(value = "book", allEntries = true)
    @Override
    public void delete(Integer id) {
        repository.delete(getById(id));
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Cacheable(value = "book")
    @Override
    public List<Book> getAllForLimit(int offset, int limit) {
        return repository.getAllForLimit(offset, limit);
    }

    @Override
    public Integer getCount() {
        return repository.getCount();
    }
}