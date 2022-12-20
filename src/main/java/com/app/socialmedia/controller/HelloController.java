package com.app.socialmedia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/api/v1/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/api/v1/hello2")
    public String hello2(@RequestParam String param) {
        return param;
    }
}
