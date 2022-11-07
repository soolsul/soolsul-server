package com.soolsul.soolsulserver.common.domain;


import com.soolsul.soolsulserver.auth.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OwnerEntity extends BaseTimeEntity{
    @CreatedBy
    @JoinColumn(name = "owner_id")
    @ManyToOne
    private User owner;
}