package com.backend.ourstory.tagtype;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagtypeRepository extends JpaRepository<Tagtype,Integer> {

    List<Tagtype> findAllBy();
}
