package com.scaler.userservice.repos;

import com.scaler.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndExpiryAtGreaterThan(String value, Date date);
}
