package com.batch.batchProcessing.admin.service;

import com.batch.batchProcessing.admin.dto.response.UserResponse;
import com.batch.batchProcessing.admin.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;

    @Override
    public UserResponse getAllUsers() {
        return null;
    }
}
