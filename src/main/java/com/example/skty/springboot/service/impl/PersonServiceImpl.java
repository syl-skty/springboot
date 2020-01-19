package com.example.skty.springboot.service.impl;

import com.example.skty.springboot.entity.Person;
import com.example.skty.springboot.repository.PersonRepository;
import com.example.skty.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * 根据人员id查询人员
     *
     * @param id
     * @return
     */
    @Override
    public Person getPersonById(Long id) {
        Assert.notNull(id, "查询人员时，id不能为空");
        return personRepository.findById(id).orElse(new Person());
    }

    /**
     * 根据人员姓名查询人员
     *
     * @param name
     * @return
     */
    @Override
    public List<Person> getPersonByName(String name) {
        return personRepository.getByName(name);
    }

    /**
     * 保存人员
     *
     * @param person
     */
    @Override
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    /**
     * 更新人员信息
     *
     * @param person
     */
    @Override
    public void updatePerson(Person person) {
        personRepository.save(person);
    }
}
