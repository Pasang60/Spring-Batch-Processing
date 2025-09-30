package com.batch.batchProcessing.admin.repository;

import com.batch.batchProcessing.admin.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
