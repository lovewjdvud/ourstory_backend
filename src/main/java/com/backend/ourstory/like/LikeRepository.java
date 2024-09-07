package com.backend.ourstory.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Integer> {

    int countByBId(long b_id);
}
