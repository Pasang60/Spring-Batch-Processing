package com.batch.batchProcessing.role.repository;

import com.batch.batchProcessing.role.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    boolean existsByName(String roleName);

    Optional<Roles> findByName(String name);
}
