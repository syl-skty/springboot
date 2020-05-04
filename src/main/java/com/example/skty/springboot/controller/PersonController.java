package com.example.skty.springboot.controller;

import com.example.skty.springboot.annotation.UrlMappingProperties;
import com.example.skty.springboot.entity.db1.Person;
import com.example.skty.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@UrlMappingProperties("classpath:mapping/person-mapping.properties")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @Value("${com.value}")
    private String name;

    /**
     * 通过人员id获取人员数据
     *
     * @param code 人员id
     * @return
     */
    @GetMapping
    public ModelAndView queryPerson(@PathVariable Long code) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("personList", personService.getPersonById(code));
        modelAndView.setViewName("/test");
        return modelAndView;
    }

    @GetMapping
    public List<Person> queryPersonByName(@PathVariable String name) {
        return personService.getPersonByName(name);
    }


}
