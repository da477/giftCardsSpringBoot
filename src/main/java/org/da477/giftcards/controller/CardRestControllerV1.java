package org.da477.giftcards.controller;

import org.da477.giftcards.model.Card;
import org.da477.giftcards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.da477.giftcards.controller.CardRestControllerV1.REST_URL;

@Validated
@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CardRestControllerV1 {

    public static final String REST_URL = "/api/v1/cards";

    private final CardService cardService;

    @Autowired
    public CardRestControllerV1(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCard() {
        List<Card> cards = cardService.getAll();

        if (cards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/lastOne")
    public Card getLastOne() {
        return cardService.getLastOne();
    }

    @GetMapping("/{number}")
    public ResponseEntity<Card> getCardByNumber(@PathVariable("number") Long number) {
        if (number == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Card card = cardService.getByNumber(number);

        if (card == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("numberNotFound", String.valueOf(number));
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(card, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Card> saveCard(@RequestBody @Validated Card card) {

        HttpHeaders headers = new HttpHeaders();

        if (card == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (card.getOwner_id() == null || card.getOwner_id().equals("")) {
            headers.add("owner_id_Mandatory", card.getOwner_id());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        cardService.save(card);
        return new ResponseEntity<>(card, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{number}")
    public ResponseEntity<Card> delete(@PathVariable("number") Long number) {

        Card card = cardService.getByNumber(number);

        if (card == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cardService.delete(number);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
