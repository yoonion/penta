package com.task.penta.repository;

import com.task.penta.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {

}
