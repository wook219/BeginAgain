package com.team3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 부분이 중요합니다.
    private Long id;
    private String content;

    public TestEntity() {}
    public TestEntity(String content){
        this.content = content;
    }


}
