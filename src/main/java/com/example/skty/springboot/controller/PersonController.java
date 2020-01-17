package com.example.skty.springboot.controller;

import com.example.skty.springboot.annotation.LoadProperties;
import com.example.skty.springboot.entity.Person;
import com.example.skty.springboot.mesg.ResponseMesg;
import com.example.skty.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@LoadProperties("classpath:mapping/person/person-mapping.properties")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @Value("${com.skty.name}")
    private String name;

    /**
     * 通过人员id获取人员数据
     * @param code 人员id
     * @return
     */
    @GetMapping
    public ResponseMesg<Person> queryPerson(@PathVariable Long code) {
        return new ResponseMesg<>(200, "success", personService.getPersonById(code));
    }

    @GetMapping
    public List<Person> queryPersonByName(@PathVariable String name) {
        return personService.getPersonByName(name);
    }


}
