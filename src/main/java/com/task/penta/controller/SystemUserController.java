package com.task.penta.controller;

import com.task.penta.dto.SystemUserCreateRequestDto;
import com.task.penta.dto.SystemUserCreateResponseDto;
import com.task.penta.dto.SystemUserSearchResponseDto;
import com.task.penta.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 회원 추가 API
     * @return
     */
    @PostMapping
    public ResponseEntity<SystemUserCreateResponseDto> createUser(@RequestBody SystemUserCreateRequestDto requestDto) {
        SystemUserCreateResponseDto createdUser = systemUserService.createUser(requestDto);

        return ResponseEntity.ok(createdUser);
    }
}
