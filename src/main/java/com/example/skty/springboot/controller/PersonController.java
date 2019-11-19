package com.example.skty.springboot.controller;

import com.example.skty.springboot.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/person")
public class PersonController {

    /**
     * 通过人员id获取人员数据
     * @param userCode 人员id
     * @return
     */
    @GetMapping("/get/code/{userCode}")
    public Person queryPerson(@PathVariable Long userCode){
        return  null;
    }

    @GetMapping("/get/name/{name}")
    public Person queryPerson(@PathVariable String name){
        return  null;
    }

}
