package org.da477.springsecurity.controller;

import org.da477.springsecurity.model.Card;
import org.da477.springsecurity.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = CardRestControllerV1.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CardRestControllerV1 {

    static final String REST_URL = "/api/v1/cards";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CardService cardService;

    @GetMapping
    public List<Card> getAll() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = auth.getPrincipal();
        log.info("CardControllerV1 getAll()");
        return cardService.findAll();
//        return cardService.findAllByStatus(Status.ACTIVE);
    }

    @GetMapping("/{number}")
    public Card getByNumber(@PathVariable Long number) {
        log.info("CardControllerV1 getByNumber {}", number);
        return cardService.getByNumber(number);
    }

    @PostMapping
    public Card create(@PathVariable Card card) {
        log.info("CardControllerV1 add new Card: " + card);
        cardService.create(card);
        return card;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        cardService.delete();
    }

}
