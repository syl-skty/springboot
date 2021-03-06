package com.example.skty.springboot.entity.db1;

import org.springframework.context.annotation.Bean;

import javax.persistence.*;

/**
 * 人员实体类
 */
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
   private String name;
    @Column(name = "address")
   private String address;
    @Column(name = "age")
   private Integer age;
    @Column(name = "is_login")
   private Boolean isLogin;

    public Person() {
    }

    public Person(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Person(Long id, String name, String address, Integer age, Boolean isLogin) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.isLogin = isLogin;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", isLogin=" + isLogin +
                '}';
    }
}
