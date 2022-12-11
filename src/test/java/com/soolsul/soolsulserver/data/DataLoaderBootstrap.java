package com.soolsul.soolsulserver.data;

import com.soolsul.soolsulserver.user.auth.business.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoaderBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final DataLoader dataLoader;
    private final RoleHierarchyService roleHierarchyService;
    private final RoleHierarchyImpl roleHierarchy;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        dataLoader.loadData();
        String allHierarchy = roleHierarchyService.AllHierarchyToString();
        roleHierarchy.setHierarchy(allHierarchy);
    }
}
