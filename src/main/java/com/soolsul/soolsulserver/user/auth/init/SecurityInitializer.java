package com.soolsul.soolsulserver.user.auth.init;

import com.soolsul.soolsulserver.user.auth.business.RoleHierarchyService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityInitializer implements ApplicationRunner {

    private RoleHierarchyService roleHierarchyService;
    private RoleHierarchyImpl roleHierarchy;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String allHierarchy = roleHierarchyService.AllHierarchyToString();
        roleHierarchy.setHierarchy(allHierarchy);
    }
}
