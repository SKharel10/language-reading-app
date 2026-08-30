package com.languageapp.backend.service;

import com.languageapp.backend.model.User;
import com.languageapp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserService {

  private final UserRepository userRepository;

  public User getOrCreateUser(Jwt jwt) {
    String auth0Id = jwt.getSubject();

    return userRepository
        .findByAuth0Id(auth0Id)
        .orElseGet(
            () -> {
              User user = new User();
              user.setAuth0Id(auth0Id);

              return userRepository.save(user);
            });
  }
}
