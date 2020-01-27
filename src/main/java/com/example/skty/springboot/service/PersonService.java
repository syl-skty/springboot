package com.example.skty.springboot.service;


import com.example.skty.springboot.entity.db1.Person;

import java.util.List;

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
    List<Person> getPersonByName(String name);

    /**
     * 保存人员
     * @param person
     * @return
     */
    void savePerson(Person person);

    /**
     * 更新人员信息
     *
     * @param person
     * @return
     */
    void updatePerson(Person person);

    List<Person> findAllPerson();
}
