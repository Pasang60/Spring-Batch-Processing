package com.batch.batchProcessing.role.config;

import com.batch.batchProcessing.role.entity.Roles;
import com.batch.batchProcessing.role.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(1)
public class RolesConfig implements CommandLineRunner {
    private final RolesRepository rolesRepository;

    @Override
    public void run(String... args) throws Exception {
        if (rolesRepository.count() == 0) {
            log.info("Roles table is empty. Initializing roles...");
            createRoleIfNotExists("ROLE_ADMIN", "Administrator role");
            createRoleIfNotExists("ROLE_USER", "User role");
            log.info("Roles initialized.");
        } else {
            log.info("Roles already exist in the database.");
        }
    }

    private void createRoleIfNotExists(String roleName, String description) {
        if (!rolesRepository.existsByName(roleName)) {
            Roles role = new Roles();
            role.setName(roleName);
            role.setDescription(description);
            rolesRepository.save(role);
            log.info("Role '{}' created.", roleName);
        }
    }
}