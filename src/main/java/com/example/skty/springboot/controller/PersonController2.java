package com.example.skty.springboot.controller;

import com.example.skty.springboot.entity.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person-test")
public class PersonController2 {

    @Value("person2文件11111")
    private String name;
    /**
     *
     * @return
     */
    @GetMapping("/get/person-{personId}")
    public Person getPerson(@PathVariable Long personId){
            return  new Person(personId, name, "");
    }
}
