package org.da477.giftcards.repository;

import org.da477.giftcards.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link User} class.
 *
 * @author da
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String username);
}
