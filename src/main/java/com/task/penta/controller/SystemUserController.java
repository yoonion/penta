package com.task.penta.controller;

import com.task.penta.dto.SystemUserSearchResponseDto;
import com.task.penta.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/system-users")
public class SystemUserController {

    private final SystemUserService systemUserService;

    @GetMapping
    public ResponseEntity<List<SystemUserSearchResponseDto>> getUsers(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userNm) {
        List<SystemUserSearchResponseDto> users = systemUserService.getUsers(userId, userNm);
        return ResponseEntity.ok(users);
    }
}
