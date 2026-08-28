package com.languageapp.backend.controller;

import com.languageapp.backend.dto.request.UserRequestDto;
import com.languageapp.backend.dto.response.UserResponseDto;
import com.languageapp.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserResponseDto> createUser
            (@Valid @RequestBody UserRequestDto request) {

        UserResponseDto response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser
            (@PathVariable UUID id) {

        Optional<UserResponseDto> response = userService.getUser(id);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser
            (@PathVariable UUID id,
             @Valid @RequestBody UserRequestDto request) {

        Optional<UserResponseDto> response = userService.updateUser(id, request);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean success = userService.deleteById(id);

        if (!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }



}
