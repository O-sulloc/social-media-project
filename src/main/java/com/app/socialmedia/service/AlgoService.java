package com.app.socialmedia.service;

import org.springframework.stereotype.Service;

@Service
public class AlgoService {

    public Long sum(Long num){
        //sum of digit
        Long answer = 0L;

        while(num > 0) {
            answer += num % 10;
            num /= 10;
        }

        return answer;
    }
}
