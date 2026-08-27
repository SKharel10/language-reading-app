package com.languageapp.backend.service;

import com.languageapp.backend.dto.request.UserRequestDto;
import com.languageapp.backend.dto.response.UserResponseDto;
import com.languageapp.backend.mapper.UserMapper;
import com.languageapp.backend.model.User;
import com.languageapp.backend.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;
  private final UserRepository userRepository;

  public UserResponseDto createUser(UserRequestDto request) {
    User user = userMapper.toEntity(request);
    userRepository.save(user);
    return userMapper.toDto(user);
  }

  public Optional<UserResponseDto> getUser(UUID id) {
    return userRepository.findById(id).map(userMapper::toDto);
  }

  public Optional<UserResponseDto> updateUser(UUID id, UserRequestDto request) {
    Optional<User> user = userRepository.findById(id);

    if (user.isEmpty()) {
      return Optional.empty();
    }

    user.get().setName(request.name());
    User saved = userRepository.save(user.get());
    return Optional.ofNullable(userMapper.toDto(saved));
  }

  public boolean deleteById(UUID id) {
    if (!userRepository.existsById(id)) {
      return false;
    }

    userRepository.deleteById(id);
    return true;
  }
}
