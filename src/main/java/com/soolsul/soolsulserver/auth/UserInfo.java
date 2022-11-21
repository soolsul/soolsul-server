package com.soolsul.soolsulserver.auth;

import com.soolsul.soolsulserver.auth.presentation.dto.RegisterRequest;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String userId;
    private String profileImage;
    private String phone;
    private String nickname;
    private String name;

    private UserInfo(String userId, String phone, String nickname, String name) {
        this.userId = userId;
        this.phone = phone;
        this.nickname = nickname;
        this.name = name;
    }

    public static UserInfo of(String userId, RegisterRequest request) {
        return new UserInfo(userId,
                request.getPhone(),
                request.getNickname(),
                request.getName());
    }
}
