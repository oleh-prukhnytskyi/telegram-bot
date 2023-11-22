package com.olehprukhnytskyi.chat.service;

import com.olehprukhnytskyi.chat.model.Role;
import com.olehprukhnytskyi.chat.util.RoleName;

public interface RoleService {
    void save(Role role);

    Role findByRoleName(RoleName roleName);
}
