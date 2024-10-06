package com.task.penta.controller;

import com.task.penta.dto.SystemUserSearchResponseDto;
import com.task.penta.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final SystemUserService systemUserService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<SystemUserSearchResponseDto> users = systemUserService.getUsers("", "");
        model.addAttribute("users", users);

        return "admin/user-list";
    }
}
