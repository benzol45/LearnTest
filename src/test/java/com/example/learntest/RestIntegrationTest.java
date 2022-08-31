package com.example.learntest;

import com.example.learntest.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application_test.properties")
@Sql(value = "/add_person_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/remove_person_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RestIntegrationTest {
    @LocalServerPort
    private Integer port;
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Value("${spring.security.user.name}")
    private String testUser;
    @Value("${spring.security.user.password}")
    private String testPassword;

    @Test
    void getAllPersonWithoutAuth() {
        String URL = "http://localhost:" + port + "/api/person";
        ResponseEntity<Person[]> response = testRestTemplate.getForEntity(URL, Person[].class);
        Person[] persons = response.getBody();

        assertEquals(401, response.getStatusCode().value());
        assertNull(persons);
    }

    @Test
    void getAllPersonWithAuth() {
        String URL = "http://localhost:" + port + "/api/person";
        ResponseEntity<Person[]> response = testRestTemplate.withBasicAuth(testUser,testPassword).getForEntity(URL, Person[].class);
        List<Person> personList = Arrays.asList(response.getBody());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2,personList.size());
        for (Person person: personList) {
            assertTrue(person.getName().equals("Test name of person") || person.getName().equals("Other Test name of person"));
        }

    }

    @Test
    void addPerson() {
        String URL = "http://localhost:" + port + "/api/person";
        Person[] before = testRestTemplate.withBasicAuth(testUser,testPassword).getForObject(URL, Person[].class);

        Person personForAdding = new Person("Random person " + new Random().nextInt(), new Random().nextInt());
        ResponseEntity<Person> response = testRestTemplate.withBasicAuth(testUser,testPassword).postForEntity(URL, personForAdding, Person.class);
        Person personFromResponse = response.getBody();
        //Прошло успешно и получили то что отправляли с новым id
        assertEquals(200, response.getStatusCode().value());
        assertEquals(personForAdding.getName(), personFromResponse.getName());
        assertEquals(personForAdding.getAge(), personFromResponse.getAge());
        assertNotEquals(0 , personFromResponse.getId());

        Person[] after = testRestTemplate.withBasicAuth(testUser,testPassword).getForObject(URL, Person[].class);

        List<Person> added = Arrays.stream(after).filter(person -> !Arrays.asList(before).contains(person)).toList();
        //В базе добавился только 1 новый и с теми аолями какие надо
        assertEquals(1, added.size());
        assertEquals(personForAdding.getName(), added.get(0).getName());
        assertEquals(personForAdding.getAge(), added.get(0).getAge());
        assertNotEquals(0 , added.get(0).getId());
    }
}