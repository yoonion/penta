package com.task.penta.entity.user.history;

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
    @Column(nullable = false)
    private Integer historyIdx;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ActionTypeEnum actionType; // C (create), U (update), D (delete)

    @Column(nullable = false)
    private Integer regUserIdx;

    @Column(nullable = false, length = 16)
    private String regIp;

    @Column(nullable = false)
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
