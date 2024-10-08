package com.task.penta.service;

import com.task.penta.common.CommonUtil;
import com.task.penta.dto.request.UserCreateRequestDto;
import com.task.penta.dto.request.UserUpdateRequestDto;
import com.task.penta.dto.response.UserCreateResponseDto;
import com.task.penta.dto.response.UserDeleteResponseDto;
import com.task.penta.dto.response.UserSearchResponseDto;
import com.task.penta.dto.response.UserUpdateResponseDto;
import com.task.penta.entity.user.SystemUser;
import com.task.penta.entity.user.SystemUserRoleEnum;
import com.task.penta.entity.user.history.ActionTypeEnum;
import com.task.penta.entity.user.history.UserHistory;
import com.task.penta.exception.CustomException;
import com.task.penta.exception.ErrorCode;
import com.task.penta.repository.SystemUserRepository;
import com.task.penta.repository.UserHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SystemUserService {

    private final SystemUserRepository systemUserRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    // 단일 회원 조회
    public UserSearchResponseDto getUserById(String userId) {
        SystemUser findUser = findByUserId(userId);

        return new UserSearchResponseDto(findUser);
    }

    // 이름으로 회원 조회
    public List<UserSearchResponseDto> getUsersByName(String userNm) {
        List<SystemUser> users;
        if (userNm != null && !userNm.isEmpty()) {
            users = systemUserRepository.findByUserNm(userNm);
        } else {
            // 비어 있을 경우 예외를 발생시키지 않고, 전체 조회를 처리하는 메서드를 호출
            return getAllUsers();
        }
        return users.stream()
                .map(UserSearchResponseDto::new)
                .collect(Collectors.toList());
    }

    // 전체 회원 조회
    public List<UserSearchResponseDto> getAllUsers() {
        List<SystemUser> users = systemUserRepository.findAll();
        return users.stream()
                .map(UserSearchResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserCreateResponseDto createUser(UserCreateRequestDto requestDto, HttpServletRequest request) {
        // 아이디 중복 검증
        String userId = requestDto.getUserId();
        if (systemUserRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_ID);
        }

        String userNm = requestDto.getUserNm();
        String userPw = passwordEncoder.encode(requestDto.getUserPw()); // 비밀번호 암호화
        String requestDtoUserAuth = requestDto.getUserAuth();
        String userAuth;
        if (requestDtoUserAuth.equals("admin")) {
            userAuth = SystemUserRoleEnum.ADMIN.getAuthority();
        } else {
            userAuth = SystemUserRoleEnum.USER.getAuthority();
        }

        SystemUser systemUser = new SystemUser(userId, userPw, userNm, userAuth);
        SystemUser savedSystemUser = systemUserRepository.save(systemUser); // 유저 정보 저장

        // 히스토리 저장
        saveUserHistory("", ActionTypeEnum.C, savedSystemUser, request);

        return new UserCreateResponseDto(savedSystemUser);
    }

    @Transactional
    public UserUpdateResponseDto updateUser(String userId, UserUpdateRequestDto requestDto, HttpServletRequest request) {
        SystemUser findUser = findByUserId(userId);

        // 회원 이름 수정
        findUser.updateUserName(requestDto.getUserNm()); // 더티 체킹 - 자동으로 update 쿼리 발생한다.

        // 히스토리 저장
        saveUserHistory("", ActionTypeEnum.U, findUser, request);

        return new UserUpdateResponseDto(findUser);
    }

    @Transactional
    public UserDeleteResponseDto deleteUser(String userId, HttpServletRequest request) {
        // 존재하는 회원인지 확인
        SystemUser findUser = findByUserId(userId);
        systemUserRepository.deleteByUserId(findUser.getUserId());

        saveUserHistory("", ActionTypeEnum.D, findUser, request);

        return new UserDeleteResponseDto(userId);
    }

    // 회원 추가(C), 수정(U), 삭제(D)에 따른 히스토리 기록. 추후 다른 서비스에서 사용한다면, service 계층으로 분리시켜 재사용 고려.
    private void saveUserHistory(String url, ActionTypeEnum actionType, SystemUser findUser, HttpServletRequest request) {
        UserHistory userHistory = UserHistory.builder()
                .url(url)
                .actionType(actionType)
                .regUserIdx(findUser.getId())
                .regIp(CommonUtil.getClientIp(request))
                .regDt(LocalDateTime.now())
                .build();
        userHistoryRepository.save(userHistory);
    }

    private SystemUser findByUserId(String userId) {
        return systemUserRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
