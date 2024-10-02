package com.task.penta.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "SYSTEM_USER")
@Getter
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Integer id;
    private String userId;
    private String userPw;
    private String userNm;
    private String userAuth;
}
