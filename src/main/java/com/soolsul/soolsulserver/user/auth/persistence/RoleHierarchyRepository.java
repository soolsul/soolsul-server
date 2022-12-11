package com.soolsul.soolsulserver.user.auth.repository;

import com.soolsul.soolsulserver.user.auth.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, String> {

    RoleHierarchy findByChildName(String roleName);
}
