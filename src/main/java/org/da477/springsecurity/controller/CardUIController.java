package org.da477.springsecurity.controller;

import org.da477.springsecurity.model.Card;
import org.da477.springsecurity.repository.CardRepository;
import org.da477.springsecurity.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cards/")
public class CardUIController {

    static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "number");
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final CardRepository repository;

    @Autowired
    public CardUIController(CardService cardService, CardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("signup")
    public String showSignUpForm(Card card) {
        log.info("signup");
        return "add-card";
    }

    @GetMapping
    public String showAll(Model model) {
        log.info("getAll");
        model.addAttribute("cards", repository.findAll(SORT_NAME));
        return "cards";
    }

    @PostMapping("add")
    public String addCard(@Valid Card card, BindingResult result, Model model) {
        log.info("addCard {}", card);
        if (result.hasErrors()) {
            return "add-card";
        }
        repository.save(card);
        return "redirect:cards";
    }

//    @DeleteMapping("delete/{number}")
    @GetMapping("delete/{number}")
    public String deleteCard(@PathVariable("number") long number, Model model) {
        log.info("deleteCard {}", number);
        Card card = repository.findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card number:" + number));

        repository.delete(card);
        model.addAttribute("cards", repository.findAll(SORT_NAME));
        return "cards";
    }

    @PostMapping("update/{number}")
    public String updateCard(@PathVariable("number") long number, @Valid Card card, BindingResult result, Model model) {
        log.info("updateCard {}", number);
        if (result.hasErrors()) {
            card.setNumber(number);
            return "update-card";
        }
        repository.save(card);
        model.addAttribute("cards", repository.findAll(SORT_NAME));
        return "cards";
    }

    @GetMapping("edit/{number}")
    public String showUpdateForm(@PathVariable("number") long number, Model model) {
        log.info("showUpdateForm {}", number);
        Card card = repository.findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card number:" + number));
        model.addAttribute("card", card);
        model.addAttribute("id", card.getId());
        model.addAttribute("number", card.getNumber());
        return "update-card";
    }


}
