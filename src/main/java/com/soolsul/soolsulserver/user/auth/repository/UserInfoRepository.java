package com.soolsul.soolsulserver.user.auth.repository;

import com.soolsul.soolsulserver.user.auth.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findByNickname(String nickname);

    void deleteByUserId(String userId);
}
