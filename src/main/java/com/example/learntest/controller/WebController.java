package com.example.learntest.controller;

import com.example.learntest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    private final PersonService personService;

    @Autowired
    public WebController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/web/person")
    public String getAllPerson(Model model) {
        model.addAttribute("persons", personService.getAll());
        return "person";
    }
}
