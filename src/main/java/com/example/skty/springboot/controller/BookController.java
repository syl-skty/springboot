package com.example.skty.springboot.controller;

import com.example.skty.springboot.annotation.UrlMappingProperties;
import com.example.skty.springboot.entity.db2.Book;
import com.example.skty.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@UrlMappingProperties("classpath:mapping/book-mapping.properties")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ModelAndView showAllBook() {
        List<Book> allBook = bookService.findAllBook();
        ModelAndView mv = new ModelAndView("book/allBook");
        mv.addObject("allBook", allBook);
        return mv;
    }

}
