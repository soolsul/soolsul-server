package com.soolsul.soolsulserver.user.auth.persistence;

import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, String>, UserQueryRepository {

    Optional<CustomUser> findByEmail(String email);

    int countByEmail(String email);
}
