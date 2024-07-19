package com.backend.ourstory.tagtype;


import com.backend.ourstory.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
public class Tagtype extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String tag_cd;
    private String content;

}
