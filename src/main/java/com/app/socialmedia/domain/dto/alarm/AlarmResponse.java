package com.app.socialmedia.domain.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmResponse {
    private List<AlarmDTO> content;
}
