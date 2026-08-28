package com.languageapp.backend.controller;

import com.languageapp.backend.dto.request.UserRequestDto;
import com.languageapp.backend.dto.response.UserResponseDto;
import com.languageapp.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private UserService userService;

    @Test
    void createUser_returns201() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "James Smith";

        UserResponseDto response =
                new UserResponseDto(id, name);

        when(userService.createUser(any(UserRequestDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "James Smith"
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("James Smith"));

        verify(userService).createUser(any(UserRequestDto.class));
    }

    @Test
    void createUser_withInvalidRequest_Returns400() throws Exception {

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": ""
                    }
                    """))
                .andExpect(status().isBadRequest());
        verify(userService, never()).createUser(any());
    }

    @Test
    void getUser_whenUserExists_returns200() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponseDto response = new UserResponseDto(id, "James Smith");

        when(userService.getUser(id)).thenReturn(Optional.of(response));

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("James Smith"));
    }


    @Test
    void getUser_whenUserDoesNotExist_returns404() throws Exception {
        UUID id = UUID.randomUUID();

        when(userService.getUser(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_whenUserExists_returns200() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponseDto response = new UserResponseDto(id, "James Smith");

        when(userService.updateUser(eq(id), any(UserRequestDto.class)))
                .thenReturn(Optional.of(response));

        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "James Smith"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("James Smith"));
    }

    @Test
    void updateUser_whenUserDoesNotExist_returns404() throws Exception {
        UUID id = UUID.randomUUID();

        when(userService.updateUser(eq(id), any(UserRequestDto.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "James Smith"
                    }
                    """))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_withInvalidRequest_returns400() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": ""
                    }
                    """))
                .andExpect(status().isBadRequest());

        verify(userService, never()).updateUser(any(), any());
    }

    @Test
    void deleteUser_whenUserExists_returns204() throws Exception {
        UUID id = UUID.randomUUID();

        when(userService.deleteById(id)).thenReturn(true);

        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_whenUserDoesNotExist_returns404() throws Exception {
        UUID id = UUID.randomUUID();

        when(userService.deleteById(id)).thenReturn(false);

        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isNotFound());
    }


}
