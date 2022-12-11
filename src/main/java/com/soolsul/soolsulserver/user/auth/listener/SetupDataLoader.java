package com.soolsul.soolsulserver.user.auth.listener;

import com.soolsul.soolsulserver.user.auth.domain.RoleHierarchy;
import com.soolsul.soolsulserver.user.auth.persistence.RoleHierarchyRepository;
import com.soolsul.soolsulserver.user.auth.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleHierarchyRepository roleHierarchyRepository;
    private boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        setupSecurityResources();
        alreadySetup = true;
    }

    @Transactional
    public void createRoleHierarchyIfNotFound(Role childRole, Role parentRole) {
        RoleHierarchy roleHierarchy = roleHierarchyRepository.findByChildName(parentRole.getRole())
                .orElseGet(() -> RoleHierarchy.builder().childName(parentRole.getRole()).build());

        RoleHierarchy parentRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);

        roleHierarchy = roleHierarchyRepository.findByChildName(childRole.getRole())
                .orElseGet(() -> RoleHierarchy.builder().childName(childRole.getRole()).build());

        RoleHierarchy childRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);
        childRoleHierarchy.setParentName(parentRoleHierarchy);
    }

    private void setupSecurityResources() {
        createRoleHierarchyIfNotFound(Role.USER, Role.ADMIN);
        createRoleHierarchyIfNotFound(Role.ANONYMOUS, Role.USER);
    }
}

