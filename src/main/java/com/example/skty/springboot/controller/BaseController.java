package com.example.skty.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    private BaseController b;

    public static void main(String[] args) {
        BaseController b = new BaseController();

        System.out.println();
    }

    public static void test() {
        test();
    }
}
