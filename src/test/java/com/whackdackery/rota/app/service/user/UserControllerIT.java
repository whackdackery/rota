package com.whackdackery.rota.app.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whackdackery.rota.app.service.user.domain.dto.UserPostDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityNotFoundException;

import static com.whackdackery.rota.app.service.user.UserTestSetups.superAdminUserOnePostDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
class UserControllerIT {

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

        MockHttpServletResponse response = mockedPostResponse(userWithMissingUsername);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Username cannot be blank");
    }

    @Test
    void postUserRejectedIfEmailIsMissing() throws Exception {
        UserPostDto userWithMissingEmail = UserPostDto.builder()
                .username("someone")
                .password("password")
                .build();

        MockHttpServletResponse response = mockedPostResponse(userWithMissingEmail);

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

        MockHttpServletResponse response = mockedPostResponse(userWithMissingEmail);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Please provide a valid email address");
    }

    @Test
    void postUserFailsWhenUserAlreadyExists() throws Exception {
        doThrow(DataIntegrityViolationException.class).when(orchestrator).createOne(superAdminUserOnePostDto());

        MockHttpServletResponse response = mockedPostResponse(superAdminUserOnePostDto());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Username or email already exists");
    }

    @Test
    void putUserIsUnsuccessful() throws Exception {
        doThrow(EntityNotFoundException.class).when(orchestrator).updateOne(1L, superAdminUserOnePostDto());

        MockHttpServletResponse response = mockedPutResponse(1L, superAdminUserOnePostDto());

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("User does not exist");
    }


    @Test
    void deleteUserIsUnsuccessful() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(orchestrator).deleteOne(1L);

        MockHttpServletResponse response = mockedDeleteResponse(1L);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("User does not exist");
    }

    private MockHttpServletResponse mockedPostResponse(UserPostDto userPostDto) throws Exception {
        MvcResult res = mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(userPostDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        return res.getResponse();
    }

    private MockHttpServletResponse mockedPutResponse(Long userId, UserPostDto userPostDto) throws Exception {
        MvcResult res = mockMvc.perform(
                put(String.format("/users/%d", userId))
                        .content(mapper.writeValueAsString(userPostDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        return res.getResponse();
    }

    private MockHttpServletResponse mockedDeleteResponse(Long userId) throws Exception {
        MvcResult res = mockMvc.perform(
                delete(String.format("/users/%d", userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        return res.getResponse();
    }
}
