package com.example.skty.springboot.service.impl;

import com.example.skty.springboot.entity.db2.Book;
import com.example.skty.springboot.repository.db2.BookRepository;
import com.example.skty.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findBooksByName(String name) {
        return bookRepository.findBooksByBookNameContains(name);
    }

    public List<Book> findBookByPriceIsGreaterThan(float price) {
        return bookRepository.findBookByPriceIsGreaterThan(price);
    }

    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

}
