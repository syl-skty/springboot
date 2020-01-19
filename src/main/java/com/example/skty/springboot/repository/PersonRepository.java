package com.example.skty.springboot.repository;

import com.example.skty.springboot.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {//JpaRepository继承了CrudRespository，除了有增删改查的功念，还扩展了一些其他功能（分页，设置查询条件等）

    @Query(" from Person where name=:name")
//使用spEL表达式，会自动帮我们生成代理类，手动书写
    List<Person> getByName(@Param("name") String name);


    /**
     * 根据用户名和年龄查询人员
     *
     * @param name
     * @param age
     * @return
     */
    //像一些简单的查询语句（只有查询语句）都是可以直接通过规范的命名规则，这时不用写注解@Query来设置，会自动识别，并生成代理类，自动生成sql
    List<Person> findPersonByNameAndAge(String name, Integer age);

    //like语句也可以不需要手动书写sql，会自动生成
    List<Person> findPersonByNameIsLike(String name);

    //同时可以在后面自己增加分页和排序参数，这时，会自动为我们按照传递的分页参数进行生成sql
    Page<Person> findPersonByNameIsLike(String name, Pageable pageable);

    List<Person> findPersonByNameIsLike(String name, Sort orders);

    //还可以使用First{数量}  ，Top{数量}这种形式，来获前面指定数量的数据
    List<Person> findFirst10ByNameIsLike(String name, Sort orders);


    //支持将查询结果以Stream的方式进行返回(jdk8)
    Stream<Person> streamByNameIsLike(String name);

}
