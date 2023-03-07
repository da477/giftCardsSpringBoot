package org.da477.springsecurity.service;

import org.da477.springsecurity.model.Card;
import org.da477.springsecurity.model.Status;
import org.da477.springsecurity.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

        card.setTypeCard(card.getTypeCard());
        card.setGenerated(true);
        card.setOwner_id(card.getOwner_id());
        card.setAmount(card.getAmount());
        card.setWithdrawal(0.0F);
        card.setStatus(Status.NEW);

        Long newNumber = null;

        long start = System.currentTimeMillis();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            newNumber = random.nextLong(start + 1);
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
            card.setLastUpdate(new Date());
            repository.save(card);
        }
    }

    public Card findLastOne() {
        return repository.findFirstByOrderByNumberDesc();
    }

    public void delete() {
        throw new UnsupportedOperationException();
    }


}
