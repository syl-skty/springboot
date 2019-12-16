package com.example.skty.springboot.repository;

import com.example.skty.springboot.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<Person, Long> {
    @Query("select #{name} from Person where name=:name")
    List<Person> getByName(@Param("name") String name);
}
