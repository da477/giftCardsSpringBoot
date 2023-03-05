package org.da477.springsecurity.service;

import org.da477.springsecurity.model.Card;
import org.da477.springsecurity.model.Status;
import org.da477.springsecurity.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CardService {

    private final CardRepository repository;

    @Autowired
    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> findAll() {
        return repository.findAll();
    }

    public List<Card> findAllByStatus(Status status) {
        return repository.findAllByStatus(status);
    }

    public Card getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Card getByNumber(Long number) {
        return repository.findByNumber(number).orElse(null);
    }

    public Card create(Card card) {
        if (card.isNew()) {
            add(card);
        } else {
            update(card);
        }
        return card;
    }

    public void add(Card card) {

        Long newNumber = null;

        long start = System.currentTimeMillis();
        long max = (long) Integer.MAX_VALUE * (long) 10;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            newNumber = random.nextLong(start + 1, max);
            if (getByNumber(newNumber) == null) {
                break;
            }
        }

        if (newNumber.equals(null) || newNumber.equals(0L)) {
            // do nothing
        } else {
            card.setNumber(newNumber);
            repository.save(card);
        }

    }

    public void update(Card card) {
        if (getById(card.getId()) != null) {
            repository.save(card);
//            logger.info("update {} with Number={}", card, card.getNumber());
        }
    }

    public void delete() {
        throw new UnsupportedOperationException();
    }


}
