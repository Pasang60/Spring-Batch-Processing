package com.batch.batchProcessing.config.user;


import com.batch.batchProcessing.admin.entity.Users;
import com.batch.batchProcessing.admin.repository.UsersRepository;
import com.batch.batchProcessing.role.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class InsertInitialUserInfo implements CommandLineRunner {

    private final UsersRepository userInfoRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_PASSWORD = "Batch@123";

    @Override
    public void run(String... args) {
        if (userInfoRepository.findByEmail("batchprocessing@gmail.com").isEmpty()) {
            Users admin = new Users();
            admin.setFullName("College Hub Admin");
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setRoles(List.of(
                    rolesRepository.findByName("ROLE_ADMIN")
                            .orElseThrow(() -> new RuntimeException("SUPER ADMIN Role not found"))
            ));
            admin.setEmail("batchprocessing@gmail.com");
            admin.setVerified(true);
            userInfoRepository.save(admin);
            log.info("Admin user inserted.");
        } else {
            log.info("Admin user already exists.");
        }
    }
}
