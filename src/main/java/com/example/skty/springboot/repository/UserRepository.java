package com.example.skty.springboot.repository;

import com.example.skty.springboot.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Person, Long> {
    @Query(" from Person where name=:name")//使用spEL表达式，会自动帮我们生成代理类
    List<Person> getByName(@Param("name") String name);
}
