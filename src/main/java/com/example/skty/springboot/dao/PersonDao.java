package com.example.skty.springboot.dao;

import com.example.skty.springboot.entity.Person;
import com.example.skty.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PersonDao {

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据当前人员的id查询人员信息
     *
     * @param id 人员id
     * @return
     */
    public Person queryPersonById(Long id) {
        userRepository.findAll(Sort.by("age desc"));
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 根据人员名称获取所有相似的人员
     *
     * @param name 人员名称
     * @return
     */
    public List<Person> queryPersonByName(String name) {
        return userRepository.getByName(name);
    }

    /**
     * 保存人员对象
     *
     * @param person
     */
    public void savePerson(Person person) {
        userRepository.save(person);
    }
}
