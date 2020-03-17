package com.example.skty.springboot.service.impl;

import com.example.skty.springboot.entity.db2.Book;
import com.example.skty.springboot.repository.db2.BookRepository;
import com.example.skty.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @CachePut(cacheNames = "book",)
    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findBooksByName(String name) {
        return bookRepository.findBooksByBookNameContains(name);
    }

    @Override
    public List<Book> findBookByPriceIsGreaterThan(float price) {
        return bookRepository.findBookByPriceIsGreaterThan(price);
    }

    @Override
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.findById(book.getId()).map(b -> bookRepository.save(book)).orElse(null);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
