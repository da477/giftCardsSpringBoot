package org.da477.springsecurity.controller;

import org.da477.springsecurity.model.Card;
import org.da477.springsecurity.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardControllerV1 {

    @Autowired
    private CardService cardService;

    @GetMapping
    public List<Card> getAll() {
        System.out.println("CardControllerV1 getAll()");
        try {
            return cardService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}")
    public Card getByNumber(@PathVariable int id) {
        System.out.println("CardControllerV1 getByNumber " + id);
        return cardService.getByNumber(id);
    }

    @PostMapping
    public Card create(@PathVariable Card card) {
        System.out.println("CardControllerV1 add new Card " + card);
        if (card.getId() == 0) {
            cardService.add(card);
        } else {
            cardService.update(card);
        }
        return card;
    }

}
