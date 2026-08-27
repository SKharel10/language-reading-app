package com.languageapp.backend.mapper;

import com.languageapp.backend.dto.request.UserRequestDto;
import com.languageapp.backend.dto.request.UserResponseDto;
import com.languageapp.backend.model.User;

public interface UserMapper {
    UserResponseDto toDto(User user);
    User toEntity (UserRequestDto request);
}
