package com.app.socialmedia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/api/v1/hello")
    public String hello() {
        return "김정현";
    }

    @GetMapping("/api/v1/{num}")
    public Long hello2(@PathVariable Long num) {
        //sum of digit
        Long answer = 0L;

        while(num > 0) {
            answer += num % 10;
            num /= 10;
        }
        return answer;
    }
}
