package com.test.demo.repository;

import com.test.demo.model.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Override
    @Query("SELECT b FROM Book b ORDER BY b.id")
    List<Book> findAll();

    @Override
    List<Book> findAll(Sort sort);

    @Query("SELECT COUNT (b) FROM Book b")
    int getCount();

    @Query(value = "SELECT * FROM book_manager ORDER BY id LIMIT NULLIF(:limit, -1) OFFSET :offset", nativeQuery = true)
    List<Book> getAllForLimit(@Param("offset")Integer offset, @Param("limit")Integer limit);
}
