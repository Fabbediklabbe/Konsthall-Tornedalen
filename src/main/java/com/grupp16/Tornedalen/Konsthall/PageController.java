package com.grupp16.Tornedalen.Konsthall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("/exhibitions")
    public String showExhibitions() {
        return "exhibitions";
    }

    // NEW
    @GetMapping("/forum")
    public String showForum() {
        return "forum";
    }
    // NEW
    @GetMapping("/mypages")
    public String showMyPages() {
        return "mypages";
    }
}
