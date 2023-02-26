package org.da477.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/login")
    public String getLoginPage() {
        return "login";

    }    @RequestMapping("/success")
    public String getSuccessPage() {
        return "success";
    }
}
