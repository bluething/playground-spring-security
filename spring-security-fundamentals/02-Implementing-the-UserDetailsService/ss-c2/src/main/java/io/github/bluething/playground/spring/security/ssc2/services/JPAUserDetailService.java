package io.github.bluething.playground.spring.security.ssc2.services;

import io.github.bluething.playground.spring.security.ssc2.entities.User;
import io.github.bluething.playground.spring.security.ssc2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class JPAUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findUserByUsername(username);

        User u = user.orElseThrow(() -> new UsernameNotFoundException("Error!"));

        return new SecurityUser(u);
    }
}
