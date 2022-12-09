package com.soolsul.soolsulserver.user.auth.domain;

import com.soolsul.soolsulserver.user.auth.presentation.dto.UserRegisterRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update user_info set deleted = true where id = ?")
@Where(clause = "deleted = false")
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

    private boolean deleted = false;

    public UserInfo(String userId, String phone, String nickname, String name) {
        this.userId = userId;
        this.phone = phone;
        this.nickname = nickname;
        this.name = name;
    }

    public static UserInfo of(String userId, UserRegisterRequest request) {
        return new UserInfo(userId,
                request.getPhone(),
                request.getNickname(),
                request.getName());
    }

    public void editNickNameAndImage(String nickName, String profileImage) {
        this.profileImage = profileImage;
        this.nickname = nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(getId(), userInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
