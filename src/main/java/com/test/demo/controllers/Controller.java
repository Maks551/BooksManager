package com.test.demo.controllers;

import com.test.demo.model.Book;
import com.test.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller
public class Controller {
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

        int total = 10;
        int maxPages = service.findAll().size()%10 == 0? service.findAll().size()%10 : (service.findAll().size()%10)-1;

        if (pageid < 1) pageid = 1;
        else if (pageid >= maxPages) pageid = maxPages;

        model.addAttribute("pageid", pageid);
        model.addAttribute("viewbooks", service.getBooksByPage(pageid,total));
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
                           @RequestParam("printYear") Integer printYear)
    {
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
}
