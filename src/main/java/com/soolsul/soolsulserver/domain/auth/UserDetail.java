package com.soolsul.soolsulserver.domain.auth;

import com.soolsul.soolsulserver.domain.common.BaseTimeEntity;
import com.soolsul.soolsulserver.domain.region.Region;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetail extends BaseTimeEntity {
    private String userId;

    @JoinColumn(name = "region_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    private String profileImage;
    private String phone;
    private String nickname;
    private String name;
}
