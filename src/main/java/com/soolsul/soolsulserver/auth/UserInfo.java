package com.soolsul.soolsulserver.auth;

import com.soolsul.soolsulserver.auth.presentation.dto.RegisterRequest;
import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo extends BaseEntity {

    private String userId;
    private String profileImage;
    private String phone;
    private String nickname;
    private String name;

    private UserInfo(String userId, String profileImage, String phone, String nickname, String name) {
        this.userId = userId;
        this.profileImage = profileImage;
        this.phone = phone;
        this.nickname = nickname;
        this.name = name;
    }

    public static UserInfo of(String userId, RegisterRequest request) {
        return new UserInfo(userId,
                request.getProfileImage(),
                request.getPhone(),
                request.getNickname(),
                request.getName());
    }
}
