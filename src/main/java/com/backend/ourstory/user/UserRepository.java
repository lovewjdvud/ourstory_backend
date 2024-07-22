package com.backend.ourstory.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findUserByEmail(String email);
}
