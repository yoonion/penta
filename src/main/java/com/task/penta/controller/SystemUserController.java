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

    /**
     * 회원 조회 API
     * @param userId (선택사항) 회원 ID
     * @param userNm (선택사항) 회원 이름
     * @return 조회된 회원 정보의 리스트를 JSON 형식으로 응답합니다. List<SystemUserSearchResponseDto> 형식입니다.
     */
    @GetMapping
    public ResponseEntity<List<SystemUserSearchResponseDto>> getUsers(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userNm) {
        List<SystemUserSearchResponseDto> users = systemUserService.getUsers(userId, userNm);

        return ResponseEntity.ok(users);
    }

    /**
     * 회원 추가 API
     * @param requestDto 회원 추가에 필요한 정보가 담긴 DTO.
     *                   JSON 형식으로 userId, userPw, userNm, userAuth를 요청 body에 포함해야 합니다.
     * @return 추가된 회원의 기본 정보를 JSON 형식으로 응답합니다. SystemUserCreateResponseDto 형식입니다.
     */
    @PostMapping
    public ResponseEntity<SystemUserCreateResponseDto> createUser(@RequestBody SystemUserCreateRequestDto requestDto) {
        SystemUserCreateResponseDto createdUser = systemUserService.createUser(requestDto);

        return ResponseEntity.ok(createdUser);
    }
}
