package com.backend.ourstory.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    boolean existsByEmail(String email);
    UserEntity findUserByEmail(String email);
}
