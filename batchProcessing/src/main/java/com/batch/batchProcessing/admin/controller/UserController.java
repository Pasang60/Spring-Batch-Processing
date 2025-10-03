package com.batch.batchProcessing.admin.controller;

import com.batch.batchProcessing.admin.dto.response.UserResponse;
import com.batch.batchProcessing.admin.service.UsersService;
import com.batch.batchProcessing.common.BaseController;
import com.batch.batchProcessing.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UsersService usersService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<UserResponse>> getAllUsers() {
        return successResponse(usersService.getAllUsers(), "Users fetched successfully");
    }
}
