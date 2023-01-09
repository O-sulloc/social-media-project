package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.alarm.AlarmDTO;
import com.app.socialmedia.domain.dto.alarm.AlarmResponse;
import com.app.socialmedia.domain.entity.Alarm;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.exception.AppException;
import com.app.socialmedia.exception.ErrorCode;
import com.app.socialmedia.repository.AlarmRepository;
import com.app.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public AlarmResponse getList(Authentication authentication, Pageable pageable) {

        // 1. 유저 검증
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // 2. 내가 받은 알람 리스트 가져오기
        Page<Alarm> alarmList = alarmRepository.findAllByUser(user, pageable);

        List<AlarmDTO> response = alarmList.stream()
                .map(Alarm::of).collect(Collectors.toList());

        return new AlarmResponse(response);
    }
}
