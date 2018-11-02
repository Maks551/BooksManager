package com.test.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "book_manager")
public class Book implements Serializable {
    private static final int START_SEQ = 1;
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "author")
    private String author;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "print_year")
    private Integer printYear;
    @Column(name = "read_already")
    private boolean readAlready;

    public Book(String title, String description, String author, String isbn, int printYear) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.isbn = isbn;
        this.printYear = printYear;
        this.readAlready = false;
    }

    public Book(Integer id, String title, String description, String author, String isbn, Integer printYear) {
        this(title, description, author, isbn, printYear);
        this.id = id;
    }
}
