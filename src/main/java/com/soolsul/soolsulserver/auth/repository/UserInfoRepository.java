package com.soolsul.soolsulserver.auth.repository;

import com.soolsul.soolsulserver.auth.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
}
