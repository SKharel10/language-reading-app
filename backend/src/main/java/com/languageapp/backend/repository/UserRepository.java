package com.languageapp.backend.repository;

import com.languageapp.backend.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByAuth0Id(String auth0Id);
}
