package com.olehprukhnytskyi.chat.repository;

import com.olehprukhnytskyi.chat.model.Role;
import com.olehprukhnytskyi.chat.util.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleName roleName);
}
