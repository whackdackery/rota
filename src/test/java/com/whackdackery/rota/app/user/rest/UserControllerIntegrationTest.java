package com.whackdackery.rota.app.user.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whackdackery.rota.app.user.model.dto.UserPostDto;
import com.whackdackery.rota.app.user.service.UserServiceOrchestrator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    ObjectMapper mapper;
    @MockBean
    UserServiceOrchestrator orchestrator;
    @Autowired
    UserController controller;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void postUserRejectedIfUserNameIsMissing() throws Exception {
        UserPostDto userWithMissingUsername = UserPostDto.builder()
                .email("something@somewhere.com")
                .password("password")
                .build();

        MockHttpServletResponse response = getMockedPostResponse(userWithMissingUsername);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Username cannot be blank");
    }

    @Test
    void postUserRejectedIfEmailIsMissing() throws Exception {
        UserPostDto userWithMissingEmail = UserPostDto.builder()
                .username("someone")
                .password("password")
                .build();

        MockHttpServletResponse response = getMockedPostResponse(userWithMissingEmail);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Please provide a valid email address");
    }

    @Test
    void postUserRejectedIfEmailIsMalformed() throws Exception {
        UserPostDto userWithMissingEmail = UserPostDto.builder()
                .username("someone")
                .email("blahblah")
                .password("password")
                .build();

        MockHttpServletResponse response = getMockedPostResponse(userWithMissingEmail);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Please provide a valid email address");
    }

    private MockHttpServletResponse getMockedPostResponse(UserPostDto userPostDto) throws Exception {
        MvcResult res = mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(userPostDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        return res.getResponse();
    }
}
