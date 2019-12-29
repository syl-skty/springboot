package com.example.skty.springboot.controller.data;

import com.example.skty.springboot.entity.Person;
import com.example.skty.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@ResponseBody
@Controller
@RequestMapping("/data/person")
public class PersonDataController {

    @Autowired
    private PersonService personService;


    @GetMapping("/get/code/{id}")
    public Person getPerson(@PathVariable("id") Long psnId){
       return  personService.getPersonById(psnId);
    }
}
