package com.test.demo.controller;

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

        Integer count = service.getCount();
        int maxPages = count%10 == 0? count/10 : (count/10)+1;

        if (pageid < 1) pageid = 1;
        else if (pageid > maxPages) pageid = maxPages;

        model.addAttribute("pageid", pageid);
        model.addAttribute("viewbooks", service.getAllForLimit((pageid-1)*TOTAL, TOTAL));
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
                           @RequestParam("printYear") String printYear){
        int printYearInt;
        try {
            printYearInt = Integer.parseInt(printYear);
        } catch (NumberFormatException e) {
            return "operations/save";
        }
        service.save(title, author, description, isbn, printYearInt);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Book book = service.getById(id);
        model.addAttribute("book", book);
        return "operations/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id){
        service.delete(id);
        return "redirect:/";
    }

    @GetMapping("/read/{id}")
    public String readBook(@PathVariable Integer id){
        service.read(id);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String readBook(@PathVariable Integer id, Model model){
        Book book = service.getById(id);
        model.addAttribute("book", book);
        return "operations/edit";
    }

    @PostMapping("/update")
    public String updateBook(@RequestParam("id") Integer id,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("description") String description,
                             @RequestParam("isbn") String isbn,
                             @RequestParam("printYear") String printYear){
        int printYearInt;
        try {
            printYearInt = Integer.parseInt(printYear);
        } catch (NumberFormatException e) {
            return "operations/save";
        }
        service.update(id, title, author, description, isbn, printYearInt);
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
        if (read.equalsIgnoreCase("true") || read.equalsIgnoreCase("yes")){
            result = true;
        } else if (read.equalsIgnoreCase("false") || read.equalsIgnoreCase("not")){
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
