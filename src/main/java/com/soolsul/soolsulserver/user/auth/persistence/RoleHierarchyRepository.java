package com.soolsul.soolsulserver.user.auth.persistence;

import com.soolsul.soolsulserver.user.auth.domain.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, String> {

    Optional<RoleHierarchy> findByChildName(String roleName);
}
