package com.task.penta.repository;

import com.task.penta.entity.user.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {

    Optional<SystemUser> findByUserId(String userId);

    List<SystemUser> findByUserNm(String userNm);

    void deleteByUserId(String userId);

    boolean existsByUserId(String userId);
}
