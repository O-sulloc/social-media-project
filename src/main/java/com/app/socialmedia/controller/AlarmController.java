package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.alarm.AlarmResponse;
import com.app.socialmedia.domain.entity.Response;
import com.app.socialmedia.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public Response<AlarmResponse> getList(@PageableDefault(size = 20, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {

        AlarmResponse alarmResponse = alarmService.getList(authentication, pageable);

        return Response.success(alarmResponse);
    }
}
