package com.task.penta.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_HISTORY")
@NoArgsConstructor
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyIdx;
    private String url;

    @Enumerated
    private ActionTypeEnum actionType; // C (create), U (update), D (delete)
    private int regUserIdx;
    private String regIp;
    private String regDt;
}
