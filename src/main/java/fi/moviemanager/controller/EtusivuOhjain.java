package fi.moviemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EtusivuOhjain {
    @GetMapping("/")
    public String home() {
        return "redirect:/elokuvat";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
