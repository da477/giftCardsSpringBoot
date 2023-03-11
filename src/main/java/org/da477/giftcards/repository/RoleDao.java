package org.da477.giftcards.repository;

import org.da477.giftcards.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
