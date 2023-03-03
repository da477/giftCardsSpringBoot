package org.da477.springsecurity.repository;

import org.da477.springsecurity.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByNumber(int number);
}
