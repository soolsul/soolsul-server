package com.soolsul.soolsulserver.user.auth.persistence;

import com.soolsul.soolsulserver.user.auth.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findByNickname(String nickname);

    Optional<UserInfo> findByUserId(String userId);

    void deleteByUserId(String userId);
}
