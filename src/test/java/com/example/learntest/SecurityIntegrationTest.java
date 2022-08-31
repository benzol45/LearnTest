package com.example.learntest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application_test.properties")
class SecurityIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void denyWithoutLogin() throws Exception {
        mockMvc.perform(get("/web/person"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Value("${spring.security.user.name}")
    private String testUser;
    @Value("${spring.security.user.password}")
    private String testPassword;
    @Test
    void correctLogin() throws Exception {
        //Имя и пароль естественно не обязательно внедрять из пропертей, можно захардкодить тестовый аккаунт если они в базе
        mockMvc.perform(formLogin().user(testUser).password(testPassword))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void incorrectLogin() throws Exception {
        mockMvc.perform(formLogin().user("incorrectUser").password("incorrectPassword"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @WithUserDetails(value = "my_test_username")
    @Test
    void accessToPageWithLogin() throws Exception  {
        mockMvc.perform(get("/web/person"))
                .andDo(print())
                .andExpect(authenticated());
    }

}