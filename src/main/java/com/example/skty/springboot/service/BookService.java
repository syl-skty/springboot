package com.example.skty.springboot.service;

import com.example.skty.springboot.entity.db2.Book;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    List<Book> findBooksByName(String name);

    List<Book> findBookByPriceIsGreaterThan(float price);

    List<Book> findAllBook();
}
