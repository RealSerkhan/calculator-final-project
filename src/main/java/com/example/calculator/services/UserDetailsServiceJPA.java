package com.example.calculator.services;


import com.example.calculator.model.User;
import com.example.calculator.model.XUserDetails;
import com.example.calculator.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceJPA implements UserDetailsService {
    private final UserRepository repo;

    public UserDetailsServiceJPA(UserRepository repo) {
        this.repo = repo;
    }

    public static XUserDetails mapper_to_XUser(User user) {
        return new XUserDetails(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                user.getOperations(),
                user.getRoles()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repo.findByEmail(s).
                map(UserDetailsServiceJPA::mapper_to_XUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User: %s isn't found in our DB with that mail", s)
                ));
    }

}
