package com.app.socialmedia.controller;

import com.app.socialmedia.service.AlgoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/hello")
public class HelloController {

    AlgoService algoService = new AlgoService();

    @GetMapping
    public String hello() {
        return "plz";
    }

    @GetMapping("/{num}")
    public Long hello2(@PathVariable Long num) {

        return algoService.sum(num);
    }
}
