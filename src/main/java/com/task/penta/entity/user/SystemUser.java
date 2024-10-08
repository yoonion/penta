package com.task.penta.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "SYSTEM_USER")
@NoArgsConstructor
@Getter
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", nullable = false)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String userId;

    @Column(nullable = false, length = 100)
    private String userPw;

    @Column(nullable = false, length = 100)
    private String userNm;

    @Column(nullable = false, length = 20)
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
