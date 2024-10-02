package com.task.penta.entity;

import com.task.penta.dto.SystemUserCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SYSTEM_USER")
@NoArgsConstructor
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

    public SystemUser(SystemUserCreateRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.userPw = requestDto.getUserPw();
        this.userNm = requestDto.getUserNm();
        this.userAuth = requestDto.getUserAuth();
    }
}
