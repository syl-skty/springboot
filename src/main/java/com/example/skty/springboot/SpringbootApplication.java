package com.example.skty.springboot;

import com.example.skty.springboot.dao.PersonDao;
import com.example.skty.springboot.datePrepar.DateBaseImitate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        DateBaseImitate.initDateBase();
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
