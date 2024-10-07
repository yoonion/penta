package com.task.penta.controller;

import com.task.penta.dto.response.UserSearchResponseDto;
import com.task.penta.entity.user.SystemUserRoleEnum;
import com.task.penta.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Secured(SystemUserRoleEnum.Authority.ADMIN) // 관리자 전용 페이지
public class AdminController {

    private final SystemUserService systemUserService;

    /**
     * 회원 list 페이지
     */
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserSearchResponseDto> users = systemUserService.getUsersByName("");
        model.addAttribute("users", users);

        return "admin/user-list";
    }

    /**
     * 회원 상세 페이지
     */
    @GetMapping("/users/{userId}")
    public String userDetail(@PathVariable String userId, Model model) {
        UserSearchResponseDto user = systemUserService.getUserById(userId);// ID로 사용자 정보를 조회
        model.addAttribute("user", user);

        return "admin/user-detail";
    }

    /**
     * 회원 수정 페이지
     */
    @GetMapping("/users/edit/{userId}")
    public String editUser(@PathVariable String userId, Model model) {
        UserSearchResponseDto user = systemUserService.getUserById(userId);
        model.addAttribute("user", user);

        return "admin/user-edit";
    }
}
