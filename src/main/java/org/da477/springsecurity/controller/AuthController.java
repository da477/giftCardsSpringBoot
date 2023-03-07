package org.da477.springsecurity.controller;

import org.da477.springsecurity.model.User;
import org.da477.springsecurity.service.SecurityService;
import org.da477.springsecurity.service.UserService;
import org.da477.springsecurity.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Controller for {@link org.da477.springsecurity.model.User}'s pages.
 *
 * @author da
 * @version 1.0
 */

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", new Date());
        setAuthUserFromContext(model);
        return "index";
    }

    @GetMapping("/auth/admin/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping(value = "/auth/admin/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult result, Model model) {

       userValidator.validate(userForm, result);

        if (result.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autoLogin(userForm.getEmail(), userForm.getConfirmPassword());

        return "redirect:/index";
    }

    @GetMapping(value = "/auth/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    public void setAuthUserFromContext(Model model) {

        User user = new User();
        String userName = "";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            userName = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            user = userService.findByEmail(userName);
        }

        model.addAttribute("userName", userName);
        model.addAttribute("user", user);

    }

}
