package org.da477.springsecurity.repository;

import org.da477.springsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
