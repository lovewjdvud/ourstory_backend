package com.backend.ourstory.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileImageRepository  extends JpaRepository<ProfileImage,String> {
    List<ProfileImageRepository> findByUserid(long userid);
}
