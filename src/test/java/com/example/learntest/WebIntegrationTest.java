package com.example.learntest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
@TestPropertySource("/application_test.properties")
@Sql(value = "/add_person_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/remove_person_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class WebIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllPerson() throws Exception {
        mockMvc.perform(get("/web/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Test name of person / 99")))
                .andExpect(content().string(Matchers.containsString("Other Test name of person / 10")));
    }
}