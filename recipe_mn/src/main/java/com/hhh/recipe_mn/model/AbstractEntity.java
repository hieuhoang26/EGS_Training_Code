package com.hhh.recipe_mn.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Column(name = "created_at", updatable = false)
    protected Instant createdAt;

    @Column(name = "updated_at")
    protected Instant updatedAt;

    @Column(name = "deleted", nullable = false)
    protected Boolean deleted = Boolean.FALSE;

    @PrePersist
    void prePersist(){
        createdAt = Instant.now();
        updatedAt = createdAt;
    }
    @PreUpdate
    void preUpdate(){
        updatedAt = Instant.now();
    }
//    @CreatedBy
//    @Column(name = "created_by", length = 50, updatable = false)
//    private String createdBy;
//
//    @LastModifiedBy
//    @Column(name = "updated_by", length = 50)
//    private String updatedBy;
}
