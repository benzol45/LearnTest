package com.example.learntest.repository;

import com.example.learntest.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonJPARepository extends JpaRepository<Person, Long> {
}
