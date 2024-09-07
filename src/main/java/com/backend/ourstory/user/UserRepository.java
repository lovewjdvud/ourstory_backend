package com.backend.ourstory.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);
    @Query("SELECT u.password FROM UserEntity u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);

    Optional<UserEntity> findUserByEmail(String email);

    Optional<UserEntity> findUserById(int id);

    UserEntity findByEmail(String email);




}
