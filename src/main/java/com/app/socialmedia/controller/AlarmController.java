package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.alarm.AlarmResponse;
import com.app.socialmedia.domain.entity.Response;
import com.app.socialmedia.service.AlarmService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "유저 알람 조회", notes = "특정 사용자의 게시글에 등록된 코멘트와 좋아요 알림을 조회합니다.")
    @GetMapping
    public Response<AlarmResponse> getList(@PageableDefault(size = 20, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {

        AlarmResponse alarmResponse = alarmService.getList(authentication, pageable);

        return Response.success(alarmResponse);
    }
}
