package com.task.penta.repository;

import com.task.penta.entity.user.history.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {

}
