package org.da477.springsecurity.repository;

import org.da477.springsecurity.model.Card;
import org.da477.springsecurity.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Card} class.
 *
 * @author da
 * @version 1.0
 */
@Transactional(readOnly = true)
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNumber(Long number);

    Card findFirstByOrderByNumberDesc();

    void deleteByNumber(Long number);

}
