package org.da477.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {

        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        Date currentTime = new Date();
        format.format(currentTime);
        // add `message` attribute
//        model.addAttribute("message", format.format(currentTime));
        model.addAttribute("message", currentTime);

        // return view name
        return "index";
    }
}
