package com.backend.ourstory.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findUserByEmail(String email);

    Optional<UserEntity> findUserById(int id);

    UserEntity findByEmail(String email);




}
