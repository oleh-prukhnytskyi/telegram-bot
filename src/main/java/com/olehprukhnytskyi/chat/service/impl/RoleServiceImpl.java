package com.olehprukhnytskyi.chat.service.impl;

import com.olehprukhnytskyi.chat.model.Role;
import com.olehprukhnytskyi.chat.repository.RoleRepository;
import com.olehprukhnytskyi.chat.service.RoleService;
import com.olehprukhnytskyi.chat.util.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Role role) {
        repository.save(role);
    }

    @Override
    public Role findByRoleName(RoleName roleName) {
        return repository.findByRoleName(roleName);
    }
}
