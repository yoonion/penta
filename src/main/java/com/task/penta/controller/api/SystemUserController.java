package com.task.penta.controller.api;

import com.task.penta.common.ApiResponse;
import com.task.penta.common.CommonUtil;
import com.task.penta.dto.request.UserCreateRequestDto;
import com.task.penta.dto.request.UserUpdateRequestDto;
import com.task.penta.dto.response.UserCreateResponseDto;
import com.task.penta.dto.response.UserDeleteResponseDto;
import com.task.penta.dto.response.UserSearchResponseDto;
import com.task.penta.dto.response.UserUpdateResponseDto;
import com.task.penta.service.SystemUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class SystemUserController {

    private final SystemUserService systemUserService;

    /**
     * 단일 회원 조회 API
     * @param userId 회원 ID
     * @return 조회된 회원 정보를 JSON 형식으로 응답합니다. UserSearchResponseDto 형식입니다.
     */
    @GetMapping("/{userId}")
    public ApiResponse<UserSearchResponseDto> getUserById(@PathVariable String userId) {
        UserSearchResponseDto user = systemUserService.getUserById(userId);

        return ApiResponse.createSuccess(user);
    }

    /**
     * 다수 회원 조회 API
     * @param userNm (선택사항) 회원 이름 / 명시하지 않을 경우, 전체 회원의 정보를 응답합니다.
     * @return 조회된 회원 정보의 리스트를 JSON 형식으로 응답합니다. List<UserSearchResponseDto> 형식입니다.
     */
    @GetMapping
    public ApiResponse<List<UserSearchResponseDto>> getUsers(
            @RequestParam(required = false) String userNm) {
        List<UserSearchResponseDto> users;
        if (userNm == null || userNm.isEmpty()) {
            users = systemUserService.getAllUsers(); // 전체 사용자 조회
        } else {
            users = systemUserService.getUsersByName(userNm); // 이름으로 조회
        }

        return ApiResponse.createSuccess(users);
    }

    /**
     * 회원 추가 API
     * @param requestDto 회원 추가에 필요한 정보가 담긴 DTO.
     *                   JSON 형식으로 userId, userPw, userNm, userAuth를 요청 body에 포함해야 합니다.
     * @return 추가된 회원의 기본 정보를 JSON 형식으로 응답합니다. SystemUserCreateResponseDto 형식입니다.
     */
    @PostMapping
    public ApiResponse<UserCreateResponseDto> createUser(
            @Valid @RequestBody UserCreateRequestDto requestDto,
            HttpServletRequest request) {
        String clientIp = getClientIp(request);
        UserCreateResponseDto createdUser = systemUserService.createUser(requestDto, clientIp);

        return ApiResponse.createSuccess(createdUser);
    }

    /**
     * 회원 수정
     * @param userId 수정할 회원의 ID. PathVariable로 입력받아 해당 회원을 수정합니다.
     * @param requestDto 수정할 회원의 이름을 요청받습니다.
     * @return 수정된 회원의 ID, 이름을 JSON 형식으로 응답합니다.
     */
    @PutMapping("/{userId}")
    public ApiResponse<UserUpdateResponseDto> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UserUpdateRequestDto requestDto,
            HttpServletRequest request) {
        String clientIp = getClientIp(request);
        UserUpdateResponseDto updatedUser = systemUserService.updateUser(userId, requestDto, clientIp);

        return ApiResponse.createSuccess(updatedUser);
    }

    /**
     * 회원 삭제 API
     * @param userId 삭제할 회원 ID. PathVariable로 입력받아 해당 회원을 삭제합니다.
     * @return 삭제된 회원의 ID를 JSON 형식으로 응답합니다.
     */
    @DeleteMapping("/{userId}")
    public ApiResponse<UserDeleteResponseDto> deleteUser(
            @PathVariable String userId, HttpServletRequest request) {
        String clientIp = getClientIp(request);
        UserDeleteResponseDto deletedUser = systemUserService.deleteUser(userId, clientIp);

        return ApiResponse.createSuccess(deletedUser);
    }

    // 클라이언트 ip 가져오기
    private static String getClientIp(HttpServletRequest request) {
        return CommonUtil.getClientIp(request);
    }
}
