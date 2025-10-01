package com.batch.batchProcessing.role.repository;

import com.batch.batchProcessing.role.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
}
