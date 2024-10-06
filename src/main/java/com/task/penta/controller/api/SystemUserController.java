package com.task.penta.controller.api;

import com.task.penta.common.ApiResponse;
import com.task.penta.dto.*;
import com.task.penta.service.SystemUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system-users")
public class SystemUserController {

    private final SystemUserService systemUserService;

    /**
     * 회원 조회 API
     * @param userId (선택사항) 회원 ID
     * @param userNm (선택사항) 회원 이름
     * @return 조회된 회원 정보의 리스트를 JSON 형식으로 응답합니다. List<SystemUserSearchResponseDto> 형식입니다.
     */
    @GetMapping
    public ApiResponse<List<SystemUserSearchResponseDto>> getUsers(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userNm) {
        List<SystemUserSearchResponseDto> users = systemUserService.getUsers(userId, userNm);

        return ApiResponse.createSuccess(users);
    }

    /**
     * 회원 추가 API
     * @param requestDto 회원 추가에 필요한 정보가 담긴 DTO.
     *                   JSON 형식으로 userId, userPw, userNm, userAuth를 요청 body에 포함해야 합니다.
     * @return 추가된 회원의 기본 정보를 JSON 형식으로 응답합니다. SystemUserCreateResponseDto 형식입니다.
     */
    @PostMapping
    public ApiResponse<SystemUserCreateResponseDto> createUser(@Valid @RequestBody SystemUserCreateRequestDto requestDto) {
        SystemUserCreateResponseDto createdUser = systemUserService.createUser(requestDto);

        return ApiResponse.createSuccess(createdUser);
    }

    /**
     * 회원 수정
     * @param userId 수정할 회원의 ID. PathVariable로 입력받아 해당 회원을 수정합니다.
     * @param requestDto 수정할 회원의 이름을 요청받습니다.
     * @return
     */
    @PutMapping("/{userId}")
    public ApiResponse<SystemUserUpdateResponseDto> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody SystemUserUpdateRequestDto requestDto) {
        SystemUserUpdateResponseDto updatedUser = systemUserService.updateUser(userId, requestDto);

        return ApiResponse.createSuccess(updatedUser);
    }

    /**
     * 회원 삭제 API
     * @param userId 삭제할 회원 ID. PathVariable로 입력받아 해당 회원을 삭제합니다.
     * @return 삭제된 회원의 ID를 JSON 형식으로 응답합니다.
     */
    @DeleteMapping("/{userId}")
    public ApiResponse<SystemUserDeleteResponseDto> deleteUser(@PathVariable String userId) {
        SystemUserDeleteResponseDto deletedUser = systemUserService.deleteUser(userId);

        return ApiResponse.createSuccess(deletedUser);
    }
}
