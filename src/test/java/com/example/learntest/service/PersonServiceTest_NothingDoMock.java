package com.example.learntest.service;

import com.example.learntest.entity.Person;
import com.example.learntest.repository.PersonJPARepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonServiceTest_NothingDoMock {
    @Test
    void NothingDoMock() {
        //создали мок
        PersonJPARepository personJPARepositoryMock = Mockito.mock(PersonJPARepository.class);
        //описали поведение
        Mockito.when(personJPARepositoryMock.findAll())
                .thenReturn(Collections.singletonList(new Person(1L,"Test person name", 30)));

        //выполнили проверку поведения
        assertEquals(Collections.singletonList(new Person(1L,"Test person name", 30)),personJPARepositoryMock.findAll());

        //проверили вызовы мока
        //ровно один раз вызван метод findAll()
        Mockito.verify(personJPARepositoryMock, Mockito.times(1)).findAll();
        //ни разу не вызван метод findById() с любым аргументом
        Mockito.verify(personJPARepositoryMock, Mockito.never()).findById(ArgumentMatchers.any());
    }
}