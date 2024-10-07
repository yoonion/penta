package com.task.penta.service;

import com.task.penta.common.CommonUtil;
import com.task.penta.dto.request.UserCreateRequestDto;
import com.task.penta.dto.request.UserUpdateRequestDto;
import com.task.penta.dto.response.UserCreateResponseDto;
import com.task.penta.dto.response.UserDeleteResponseDto;
import com.task.penta.dto.response.UserSearchResponseDto;
import com.task.penta.dto.response.UserUpdateResponseDto;
import com.task.penta.entity.user.history.ActionTypeEnum;
import com.task.penta.entity.user.SystemUser;
import com.task.penta.entity.user.SystemUserRoleEnum;
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

    public List<UserSearchResponseDto> getUsers(String userId, String userNm) {
        List<SystemUser> users;

        // 조건에 따라 사용자 조회 (파라미터가 있는 경우 -> 해당 조건으로 검색하고, 파라미터가 없는 경우 모든 회원을 반환)
        // 회원ID & 회원 이름을 모두 입력하지 않은 경우
        if (userId != null && !userId.isEmpty() && userNm != null && !userNm.isEmpty()) {
            users = systemUserRepository.findByUserIdAndUserNm(userId, userNm);
        } else if (userId != null && !userId.isEmpty()) { // userId만 입력한 경우
            users = systemUserRepository.findByUserId(userId);
        } else if (userNm != null && !userNm.isEmpty()) { // userNm만 입력한 경우
            users = systemUserRepository.findByUserNm(userNm);
        } else {
            users = systemUserRepository.findAll(); // 모든 사용자 조회
        }

        // SystemUser 객체 DTO로 변환
        return users.stream()
                .map(UserSearchResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserCreateResponseDto createUser(UserCreateRequestDto requestDto, HttpServletRequest request) {
        // 아이디 중복 검증
        String userId = requestDto.getUserId();
        List<SystemUser> findUser = findByUserId(userId);
        if(!findUser.isEmpty()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_ID);
        }

        String userNm = requestDto.getUserNm();
        String userPw = passwordEncoder.encode(requestDto.getUserPw()); // 비밀번호 암호화
        String userAuth = SystemUserRoleEnum.USER.getAuthority(); // 일반 회원만 가입이 가능하도록 설정

        SystemUser systemUser = new SystemUser(userId, userPw, userNm, userAuth);
        SystemUser savedSystemUser = systemUserRepository.save(systemUser); // 유저 정보 저장

        // 히스토리 저장
        saveUserHistory("", ActionTypeEnum.C, savedSystemUser, request);

        return new UserCreateResponseDto(savedSystemUser);
    }

    @Transactional
    public UserUpdateResponseDto updateUser(String userId, UserUpdateRequestDto requestDto, HttpServletRequest request) {
        List<SystemUser> findUser = findByUserId(userId);

        // 존재하는 회원인지 확인
        if (findUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        // 회원 이름 수정
        SystemUser user = findUser.get(0);
        user.updateUserName(requestDto.getUserNm()); // 더티 체킹 - 자동으로 update 쿼리 발생한다.

        // 히스토리 저장
        saveUserHistory("", ActionTypeEnum.U, user, request);

        return new UserUpdateResponseDto(user);
    }

    @Transactional
    public UserDeleteResponseDto deleteUser(String userId, HttpServletRequest request) {
        // 존재하는 회원인지 확인
        List<SystemUser> findUser = findByUserId(userId);
        if (findUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        systemUserRepository.deleteByUserId(userId);

        saveUserHistory("", ActionTypeEnum.D, findUser.get(0), request);

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

    private List<SystemUser> findByUserId(String userId) {
        return systemUserRepository.findByUserId(userId);
    }
}
