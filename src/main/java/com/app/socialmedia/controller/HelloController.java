package com.app.socialmedia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/hello")
public class HelloController {


    @GetMapping
    public String hello() {
        return "plz";
    }

}
