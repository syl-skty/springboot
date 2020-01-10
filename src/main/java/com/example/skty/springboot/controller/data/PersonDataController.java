package com.example.skty.springboot.controller.data;

import com.example.skty.springboot.annotation.LoadProperties;
import com.example.skty.springboot.entity.Person;
import com.example.skty.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
@ResponseBody
@Controller
@LoadProperties(path = "classpath:mapping/person/person-mapping.properties")
public class PersonDataController {

    @Autowired
    private PersonService personService;


    @GetMapping
    public Person getPerson(@PathVariable("id") Long psnId){
        int a = 1 / 0;
       return  personService.getPersonById(psnId);
    }
}
