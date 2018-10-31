package com.test.demo.controllers;

import com.test.demo.model.Book;
import com.test.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    private static int TOTAL = 10;
    private BookService service;
    @Autowired
    public void setService(BookService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String start(Model model){
        return get(1, model);
    }
    @RequestMapping(value = "/{pageid}")
    public String get(@PathVariable int pageid, Model model){

        List<Book> bookList = service.findAll();
        int bookListSize = bookList.size();
        int maxPages = bookListSize%10 == 0? bookListSize/10 : (bookListSize/10)+1;

        if (pageid < 1) pageid = 1;
        else if (pageid > maxPages) pageid = maxPages;

        model.addAttribute("pageid", pageid);
        model.addAttribute("viewbooks", service.getBooksByPage(pageid, TOTAL, bookList));
        return "index";
    }

    @GetMapping("/new")
    public String newNote() {
        return "operations/save";
    }
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String saveBook(@RequestParam("title") String title,
                           @RequestParam("author") String author,
                           @RequestParam("description") String description,
                           @RequestParam("isbn") String isbn,
                           @RequestParam("printYear") Integer printYear){
        service.saveBook(title, author, description, isbn, printYear);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Book book = service.getBookById(id);
        model.addAttribute("book", book);
        return "operations/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id){
        service.deleteBook(id);
        return "redirect:/";
    }

    @GetMapping("/read/{id}")
    public String readBook(@PathVariable Integer id){
        service.readBook(id);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String readBook(@PathVariable Integer id, Model model){
        Book book = service.getBookById(id);
        model.addAttribute("book", book);
        return "operations/edit";
    }

    @PostMapping("/update")
    public String updateBook(@RequestParam("id") Integer id,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("isbn") String isbn,
                             @RequestParam("printYear") Integer printYear){
        service.updateBook(id, title, description, isbn, printYear);
        return  "redirect:/";
    }

    @GetMapping("/search")
    public String search(){
        return "operations/search";
    }
    @GetMapping("/searchByTitle")
    public String searchByTitle(@RequestParam String title, Model model){
        List<Book> result = new ArrayList<>();
        for (Book book : service.findAll()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) result.add(book);
        }
        model.addAttribute("books", result);
        return "operations/find";
    }
    @GetMapping("/searchByAuthor")
    public String searchByAuthor(@RequestParam String author, Model model){
        List<Book> result = new ArrayList<>();
        for (Book book : service.findAll()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) result.add(book);
        }
        model.addAttribute("books", result);
        return "operations/find";
    }

    @GetMapping("/searchByRead")
    public String searchByRead(@RequestParam String read, Model model){
        boolean result;
        if (read.equalsIgnoreCase("true") || read.equalsIgnoreCase("yes") || read.equalsIgnoreCase("y")){
            result = true;
        } else if (read.equalsIgnoreCase("false") || read.equalsIgnoreCase("no") || read.equalsIgnoreCase("not")){
            result = false;
        } else return "operations/search";
        List<Book> resultList = new ArrayList<>();
        for (Book book : service.findAll()) {
            if (book.isReadAlready() == result) resultList.add(book);
        }
        model.addAttribute("books", resultList);
        return "operations/find";
    }
    @GetMapping("/searchByYear")
    public String searchByYear(@RequestParam int year, Model model){
        List<Book> result = new ArrayList<>();
        for (Book book : service.findAll()) {
            if (book.getPrintYear().equals(year)) result.add(book);
        }
        model.addAttribute("books", result);
        return "operations/find";
    }
}
