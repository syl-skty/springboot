package com.example.skty.springboot.service;


import com.example.skty.springboot.entity.Person;

/**
 * 人员服务类
 */

public interface PersonService {
    /**
     * 根据人员id查询人员
     * @param id
     * @return
     */
    Person getPersonById(Long id);

    /**
     * 根据人员姓名查询人员
     * @param name
     * @return
     */
    Person getPersonByName(String name);
}
