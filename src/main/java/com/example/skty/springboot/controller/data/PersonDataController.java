package com.example.skty.springboot.controller.data;

import com.example.skty.springboot.annotation.UrlMappingProperties;
import com.example.skty.springboot.entity.db1.Person;
import com.example.skty.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@UrlMappingProperties("classpath:mapping/person-mapping.properties")
public class PersonDataController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPerson() {
        return personService.findAllPerson();
    }

    @GetMapping
    public Person getPerson(@PathVariable("id") Long psnId) {
        int a = 1 / 0;
        return personService.getPersonById(psnId);
    }
}
