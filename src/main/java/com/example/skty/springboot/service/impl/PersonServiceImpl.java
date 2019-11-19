package com.example.skty.springboot.service.impl;

import com.example.skty.springboot.dao.PersonDao;
import com.example.skty.springboot.entity.Person;
import com.example.skty.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    /**
     * 根据人员id查询人员
     *
     * @param id
     * @return
     */
    @Override
    public Person getPersonById(Long id) {
        Assert.notNull(id, "查询人员时，id不能为空");
        return null;
    }

    /**
     * 根据人员姓名查询人员
     *
     * @param name
     * @return
     */
    @Override
    public Person getPersonByName(String name) {
        return null;
    }
}
