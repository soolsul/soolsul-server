package com.soolsul.soolsulserver.user.auth.repository;

import com.soolsul.soolsulserver.user.auth.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CustomUser, String>, UserQueryRepository {

    CustomUser findByEmail(String email);

    int countByEmail(String email);
}
