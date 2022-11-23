package com.nrr.project.authserver.repository;

import com.nrr.project.authserver.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}
