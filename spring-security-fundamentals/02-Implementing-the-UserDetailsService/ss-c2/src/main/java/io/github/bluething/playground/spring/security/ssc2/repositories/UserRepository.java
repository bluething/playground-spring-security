package io.github.bluething.playground.spring.security.ssc2.repositories;

import io.github.bluething.playground.spring.security.ssc2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);
}
