package com.example.skty.springboot.controller;

import com.example.skty.springboot.configurations.value.ControllerUrlMapping;
import com.example.skty.springboot.entity.Person;
import com.example.skty.springboot.mesg.ResponseMesg;
import com.example.skty.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/person")
public class PersonController {


    @Autowired
    private PersonService personService;

    @Value("${com.skty.name}")
    private String name;

    private String name1;
    /**
     * 通过人员id获取人员数据
     * @param userCode 人员id
     * @return
     */
    @GetMapping
    public ResponseMesg<Person> queryPerson(@PathVariable("code") Long userCode) {
        return new ResponseMesg<>(200, "success", personService.getPersonById(userCode));
    }

    @GetMapping
    public List<Person> queryPersonByName(@PathVariable String name) {
        ControllerUrlMapping m = new ControllerUrlMapping();
        return personService.getPersonByName(ControllerUrlMapping.queryPerson);
    }


}
