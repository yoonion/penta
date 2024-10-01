package com.task.penta.controller;

import com.task.penta.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService systemUserService;
}
