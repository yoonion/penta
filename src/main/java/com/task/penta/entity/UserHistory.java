package com.task.penta.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_HISTORY")
@NoArgsConstructor
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyIdx;
    private String url;

    @Enumerated(value = EnumType.STRING)
    private ActionTypeEnum actionType; // C (create), U (update), D (delete)

    private Integer regUserIdx;
    private String regIp;
    private LocalDateTime regDt;

    @Builder
    public UserHistory(String url, ActionTypeEnum actionType, int regUserIdx, String regIp, LocalDateTime regDt) {
        this.url = url;
        this.actionType = actionType;
        this.regUserIdx = regUserIdx;
        this.regIp = regIp;
        this.regDt = regDt;
    }
}
