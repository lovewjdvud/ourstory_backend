package com.backend.ourstory.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmail(String email);
}
