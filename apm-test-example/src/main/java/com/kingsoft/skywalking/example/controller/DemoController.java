package com.kingsoft.skywalking.example.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Jack
 * @Date: 2021/11/9 22:03
 * @Description:
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @GetMapping("/add/{num1}/{num2}")
    public int add(@PathVariable(name = "num1") int num1, @PathVariable(name = "num2") int num2) {
        return num1 + num2;
    }
}
