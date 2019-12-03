package com.example.skty.springboot.controller;

import com.example.skty.springboot.entity.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Value("${com.skty.test.name}")
    private String name;
    @Value("${com.skty.test.name1}")
    private String name1;
    /**
     * 通过人员id获取人员数据
     * @param userCode 人员id
     * @return
     */
    @GetMapping("/get/code/{userCode}")
    public Person queryPerson(@PathVariable Long userCode){
        return  new Person(1L, name, "地址");
    }

    @GetMapping("/get/name/{name}")
    public Person queryPerson(@PathVariable String name){
        return  new Person(2L, name1, "地址1");
    }

}
