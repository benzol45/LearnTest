package com.example.learntest.controller;

import com.example.learntest.entity.Person;
import com.example.learntest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final PersonService personService;

    @Autowired
    public RestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/api/person")
    public List<Person> getAllPerson() {
        return personService.getAll();
    }

    @PostMapping("/api/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.add(person);
    }
}
