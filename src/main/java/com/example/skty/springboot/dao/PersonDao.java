package com.example.skty.springboot.dao;

import com.example.skty.springboot.entity.Person;
import org.springframework.stereotype.Repository;


@Repository
public class PersonDao {
    /**
     * 根据当前人员的id查询人员信息
     * @param id 人员id
     * @return
     */
    public  Person queryPersonById(Long id){
        return null;
    }

    /**
     * 根据人员名称获取所有相似的人员
     * @param name 人员名称
     * @return
     */
    public Person quryPersonByName(String name){
        return  null;
    }
}
