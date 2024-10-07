package com.task.penta.entity.user;

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

    public SystemUser(String userId, String userPw, String userNm, String userAuth) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNm = userNm;
        this.userAuth = userAuth;
    }

    // 회원 이름 수정
    public void updateUserName(String userNm) {
        this.userNm = userNm;
    }
}
