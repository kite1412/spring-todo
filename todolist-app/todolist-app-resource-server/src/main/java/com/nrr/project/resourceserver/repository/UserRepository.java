package com.nrr.project.resourceserver.repository;

import com.nrr.project.resourceserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
