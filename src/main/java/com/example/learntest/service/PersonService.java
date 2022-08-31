package com.example.learntest.service;

import com.example.learntest.entity.Person;
import com.example.learntest.repository.PersonJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonJPARepository personJPARepository;

    @Autowired
    public PersonService(PersonJPARepository personJPARepository) {
        this.personJPARepository = personJPARepository;
    }

    public List<Person> getAll() {
        return personJPARepository.findAll();
    }

    public Person add(Person person) {
        return personJPARepository.save(person);
    }
}
