package com.task.penta.repository;

import com.task.penta.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {

    List<SystemUser> findByUserIdAndUserNm(String userId, String userNm);

    List<SystemUser> findByUserId(String userId);

    List<SystemUser> findByUserNm(String userNm);

    void deleteByUserId(String userId);
}
