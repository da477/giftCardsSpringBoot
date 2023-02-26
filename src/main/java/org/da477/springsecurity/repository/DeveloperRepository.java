package org.da477.springsecurity.repository;

import org.da477.springsecurity.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

}
