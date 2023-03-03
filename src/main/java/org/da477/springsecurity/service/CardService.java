package org.da477.springsecurity.service;

import org.da477.springsecurity.model.Card;
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

    public Card getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Card getByNumber(int number) {
        return repository.findByNumber(number).orElse(null);
    }

    public void add(Card card) {

        int newNumber;

        int start = (int) System.currentTimeMillis();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            newNumber = random.nextInt(start, Integer.MAX_VALUE);
            if (getByNumber(newNumber) == null) {
                break;
            }
        }

//        if (newNumber == null) {
//            throw new NotFoundException("Valid number wasn't generated for a new Card.");
//        }
//        card.setNumber(newNumber);

        repository.save(card);

    }

    public void update(Card card) {
        if (getById(card.getId()) != null) {
            repository.save(card);
//            logger.info("update {} with Number={}", card, card.getNumber());
        }
    }


}
