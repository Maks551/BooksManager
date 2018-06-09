package com.test.demo.services;

import com.test.demo.model.Book;
import com.test.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;
    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book getBookById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public void saveBook(String title, String author, String description,
                         String isbn, Integer printYear){
        repository.save(new Book(title, author ,description, isbn, printYear));
        overtaking();
    }

    private void overtaking(){
        for (int i = 0; i < repository.findAll().size(); i++) {
            repository.getOne(i+1).setId(i+1);
        }
    }

    @Override
    public void updateBook(Integer id, String title, String description, String isbn, Integer printYear) {
        Book book = repository.getOne(id);
        book.setTitle(title);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setPrintYear(printYear);
        book.setReadAlready(false);
        repository.save(book);
    }

    @Override
    public void readBook(Integer id) {
        Book book = repository.getOne(id);
        if (!book.isReadAlready()) {
            book.setReadAlready(true);
            repository.save(book);
        }
    }

    @Override
    public void deleteBook(Integer id) {
        repository.delete(getBookById(id));
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Book> getBooksByPage(int pageid, int total){
        List<Book> list = new ArrayList<>();
        if (repository.findAll().size()>0) {
            for (int i = (pageid-1) * total; i < repository.findAll().size(); i++) {
                if (i == (pageid-1) * total + 10) break;
                list.add(getBookById(i+1));
            }
        }
        return list;
    }
}
