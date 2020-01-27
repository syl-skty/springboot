package com.example.skty.springboot.controller.data;

import com.example.skty.springboot.annotation.LoadProperties;
import com.example.skty.springboot.entity.db2.Book;
import com.example.skty.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@LoadProperties("classpath:mapping/book-mapping.properties")
public class BookDataController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBook() {
        return bookService.findAllBook();
    }

    @GetMapping
    public List<Book> findBookByName(@PathVariable String name) {
        return bookService.findBooksByName(name);
    }
}
