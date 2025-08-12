package com.example.daotrungtin_smilegate_testassigment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "redirect:/games";
    }

    @GetMapping("/games")
    public String games() {
        return "games";
    }

    @GetMapping("/games/register")
    public String createForm(Model model) {
        model.addAttribute("gameId", null);
        return "game-form";
    }

    @GetMapping("/games/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("gameId", id);
        return "game-form";
    }
}

