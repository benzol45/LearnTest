package com.example.learntest.service;

import com.example.learntest.entity.Person;
import com.example.learntest.repository.PersonJPARepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@SpringBootTest //По идее достаточно @ExtendWith(SpringExtension.class) но тогда не создаётся весь контекст и не пройдёт инжект сервиса
class PersonServiceTest_SpringMock {
    private final PersonService personService;

    @Autowired
    PersonServiceTest_SpringMock(PersonService personService) {
        this.personService = personService;
    }

    //создаём мок-бин. он заменит оригинальный бин и будет инжектиться везде где нужен
    @MockBean
    private PersonJPARepository personJPARepositoryMock;


    @Test
    void getAll() {
        //обучаем новедение мок-бина
        Mockito.when(personJPARepositoryMock.findAll())
                .thenReturn(Collections.singletonList(new Person(1L,"Test person name", 30)));

        //выполнили проверку поведения сервиса
        assertEquals(Collections.singletonList(new Person(1L,"Test person name", 30)),personService.getAll());

        //проверили вызовы мока
        //ровно один раз вызван метод findAll()
        Mockito.verify(personJPARepositoryMock, Mockito.times(1)).findAll();
        //ни разу не вызван метод findById() с любым аргументом
        Mockito.verify(personJPARepositoryMock, Mockito.never()).findById(ArgumentMatchers.any());

    }
}