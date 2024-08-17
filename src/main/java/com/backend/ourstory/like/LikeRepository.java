package com.backend.ourstory.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Integer> {

    int countByB_id(long b_id);
}
