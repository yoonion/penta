package com.task.penta.service;

import com.task.penta.dto.*;
import com.task.penta.entity.SystemUser;
import com.task.penta.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SystemUserService {

    private final SystemUserRepository systemUserRepository;

    public List<SystemUserSearchResponseDto> getUsers(String userId, String userNm) {
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
                .map(SystemUserSearchResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SystemUserCreateResponseDto createUser(SystemUserCreateRequestDto requestDto) {
        // 아이디 중복 검증
        String userId = requestDto.getUserId();
        List<SystemUser> findUser = systemUserRepository.findByUserId(userId);
        if(!findUser.isEmpty()) {
            throw new IllegalArgumentException("중복된 회원 ID 입니다.");
        }

        SystemUser systemUser = new SystemUser(requestDto);
        SystemUser savedSystemUser = systemUserRepository.save(systemUser);

        return new SystemUserCreateResponseDto(savedSystemUser);
    }

    @Transactional
    public SystemUserUpdateResponseDto updateUser(SystemUserUpdateRequestDto requestDto) {
        String userId = requestDto.getUserId();
        List<SystemUser> findUser = systemUserRepository.findByUserId(userId);

        // 존재하는 회원인지 확인
        if (findUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원ID 입니다.");
        }

        // 회원 이름 수정
        SystemUser user = findUser.get(0);
        user.updateUserName(requestDto.getUserNm()); // 더티 체킹 - 자동으로 update 쿼리 발생한다.

        return new SystemUserUpdateResponseDto(user);
    }
}
