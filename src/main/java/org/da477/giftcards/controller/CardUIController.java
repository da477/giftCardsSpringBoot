package org.da477.giftcards.controller;

import org.da477.giftcards.model.Card;
import org.da477.giftcards.repository.CardRepository;
import org.da477.giftcards.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cards/")
public class CardUIController {

    static final Sort SORT_ID = Sort.by(Sort.Direction.DESC, "id");
    private final Pageable DEFAULT_pageAndSortedById = PageRequest.of(0, 10, SORT_ID);
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final CardRepository repository;
    private final CardService cardService;

    @Autowired
    public CardUIController(CardService cardService, CardRepository repository) {
        this.repository = repository;
        this.cardService = cardService;
    }

    @GetMapping("signup")
    public String showSignUpForm(Card card) {
        log.info("add-card CardUiController");
        return "add-card";
    }

    @GetMapping
    public String showAll(HttpServletRequest request, Model model) {
        log.info("showAll CardUiController");

        int page = 0; //default page number is 0 (yes it is weird)
        int size = 10; //default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        Pageable pageAndSortedById = PageRequest.of(page, size, SORT_ID);

        model.addAttribute("cards", repository.findAll(pageAndSortedById));

        return "cards";
    }

    @PostMapping("add")
    public String addCard(@ModelAttribute("card") Card card, BindingResult result, Model model) {
        log.info("add new Card {}", card);
        if (result.hasErrors()) {
            return "add-card";
        }

        cardService.save(card);

        return "redirect:/cards/";
    }

    @GetMapping("delete/{number}")
    public String deleteCard(@PathVariable("number") long number, Model model) {
        log.info("deleteCard {}", number);
        Card card = repository.findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card number:" + number));

        repository.delete(card);
        model.addAttribute("cards", repository.findAll(DEFAULT_pageAndSortedById));
        return "cards";
    }

    @PostMapping("update/{number}")
    public String updateCard(@PathVariable("number") long number, @ModelAttribute("card") Card card, BindingResult result, Model model) {
        log.info("updateCard {}", number);
        if (result.hasErrors()) {
            card.setNumber(number);
            return "update-card";
        }
        Card cardFromDB = repository.findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card number:" + number));

        cardFromDB.setTypeCard(card.getTypeCard());
        cardService.save(cardFromDB);
        model.addAttribute("cards", repository.findAll(DEFAULT_pageAndSortedById));
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
