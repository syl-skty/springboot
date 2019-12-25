package com.example.skty.springboot;

import com.example.skty.springboot.annotation.LoadPropertyToBeanUtil;
import com.example.skty.springboot.configurations.value.ControllerUrlMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        LoadPropertyToBeanUtil.loadProperties(ControllerUrlMapping.class);
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
