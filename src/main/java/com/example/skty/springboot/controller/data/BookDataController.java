package com.example.skty.springboot.controller.data;

import com.example.skty.springboot.annotation.UrlMappingProperties;
import com.example.skty.springboot.entity.db2.Book;
import com.example.skty.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@UrlMappingProperties("classpath:mapping/book-mapping.properties")
public class BookDataController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @Cacheable("allBook")
    public List<Book> getAllBook() {
        return bookService.findAllBook();
    }


    @GetMapping
    public List<Book> findBookByName(@PathVariable String name) {
        return bookService.findBooksByName(name);
    }

    @PostMapping
    public Book updateBook(Book book) {
        return bookService.updateBook(book);
    }

    @PostMapping
    public Book saveBook(Book book) {
        return bookService.addBook(book);
    }

    @CacheEvict()
    @PostMapping
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
