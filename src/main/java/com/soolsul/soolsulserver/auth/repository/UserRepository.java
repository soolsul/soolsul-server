package com.soolsul.soolsulserver.auth.repository;

import com.soolsul.soolsulserver.auth.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CustomUser, String> {

    CustomUser findByEmail(String email);

    int countByEmail(String email);
}
