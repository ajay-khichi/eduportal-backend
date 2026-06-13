package com.ignishers.backend.repository.user;

import com.ignishers.backend.model.enums.RoleName;
import com.ignishers.backend.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

     boolean existsByRoleName(RoleName name);

     Optional<Role> findByRoleName(RoleName roleName);
}
