package com.app.socialmedia.domain.entity;

import com.app.socialmedia.domain.dto.alarm.AlarmDTO;
import com.app.socialmedia.domain.dto.alarm.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@SQLDelete(sql = "UPDATE alarm SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
public class Alarm extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private AlarmType alarmType; // like 알람인지 comment 알람인지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 내 user id

    private Long fromUserId; // 내 글에 댓글, 좋아요 한 유저 id
    private Long targetId; // 내가 쓴 post id
    private String text;
    private LocalDateTime deletedAt;

    public static Alarm addAlarm(AlarmType alarmType, User user, Long fromUserId, Long targetId) {
        return Alarm.builder()
                .alarmType(alarmType)
                .user(user)
                .fromUserId(fromUserId)
                .targetId(targetId)
                .text(alarmType.getText())
                .build();
    }

    public static AlarmDTO of(Alarm alarmList) {
        return AlarmDTO.builder()
                .id(alarmList.getId())
                .alarmType(String.valueOf(alarmList.getAlarmType()))
                .fromUserId(alarmList.fromUserId)
                .targetId(alarmList.targetId)
                .text(alarmList.text)
                .createdAt(alarmList.getRegisteredAt())
                .build();
    }

}
