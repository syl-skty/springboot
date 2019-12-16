package com.example.skty.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SpringbootApplication.class, args);
        List<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        System.out.println(a.stream().collect(Collectors.joining(
                ","
                , "\"", "\"")));
    }

}
