package org.da477.giftcards.service;

import lombok.extern.slf4j.Slf4j;
import org.da477.giftcards.model.Card;
import org.da477.giftcards.model.Status;
import org.da477.giftcards.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service interface for {@link Card} class.
 *
 * @author da
 * @version 1.0
 */
@Service
@Slf4j
public class CardService {

    private final CardRepository repository;

    @Autowired
    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> getAll() {
        log.info("IN CardService getAll");
        return repository.findAll();
    }

    public Card getById(Long id) {
        log.info("IN CardService getById {}", id);
        return repository.findById(id).orElse(null);
    }

    public Card getByNumber(Long number) {
        return repository.findByNumber(number).orElse(null);
    }

    public Card getLastOne() {
        log.info("IN CardService getLastOne");
        return repository.findFirstByOrderByNumberDesc();
    }

    public Card save(Card card) {
        log.info("IN CardService save {}", card);
        if (card.isNew() && getByNumber(card.getNumber()) == null) {
            addNewCard(card);
        } else {
            updateCard(card);
        }
        return card;
    }

    private void updateCard(Card card) {
        Card fromDB = getByNumber(card.getNumber());
        card.setId(fromDB.getId());
        card.setTypeCard(fromDB.getTypeCard());
        card.setGenerated(fromDB.isGenerated());
        card.setRegistered(fromDB.getRegistered());

        card.setStatus(fromDB.getStatus() == Status.NEW || fromDB.getStatus() == Status.ACTIVE ? Status.ACTIVE : fromDB.getStatus());
        card.setAmount(fromDB.getAmount());
        card.setPrint(fromDB.isPrint() == true ? true : card.isPrint());
        card.setLastUpdate(new Date());
        card.setWithdrawal(card.getWithdrawal() != null && card.getWithdrawal() > fromDB.getWithdrawal() ? card.getWithdrawal() : fromDB.getWithdrawal());
        card.setOwner_id(fromDB.getOwner_id() == null ? card.getOwner_id() : fromDB.getOwner_id());

        repository.save(card);
    }

    private void addNewCard(Card card) {

        card.setTypeCard(card.getTypeCard());
        card.setGenerated(true);
        card.setOwner_id(card.getOwner_id());
        card.setAmount(card.getAmount());
        card.setWithdrawal(0.0F);
        card.setStatus(Status.NEW);
        card.setPrint(false);

        Long newNumber = generateNewNumber();

        if (newNumber.equals(null) || newNumber.equals(0L)) {
            // do nothing
        } else {
            card.setNumber(newNumber);
            repository.save(card);
        }

    }

    public Long generateNewNumber() {

        Long newNumber = null;

        long start = System.currentTimeMillis();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            newNumber = random.nextLong(start + 1);
            if (getByNumber(newNumber) == null) {
                break;
            }
        }
        return newNumber;
    }


    public void delete(Long number) {
        log.info("IN CardService delete {}", number);
        repository.deleteByNumber(number);
        repository.flush();
    }

}
