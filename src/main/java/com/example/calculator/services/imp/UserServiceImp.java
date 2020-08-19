package com.example.calculator.services.imp;

import com.example.calculator.exception.UserNotFoundException;
import com.example.calculator.model.Operation;
import com.example.calculator.model.User;
import com.example.calculator.model.XUserDetails;
import com.example.calculator.repository.UserRepository;
import com.example.calculator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setRoles("USER");
        return userRepository.save(user);
    }


    @Override
    public void addOperation(String email, Operation operation) {
        Optional<User> users = userRepository.findByEmail(email);
        User user = users.orElseThrow(RuntimeException::new);
        System.out.println("Email " + email);
        user.getOperations().add(operation);
        System.out.println(operation.getOperation());
        user.getOperations().forEach(a -> System.out.println(a.getOperation()));

        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }




    public static String getUserNameFromPrincipal(Principal p) {

        if (p instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken user = (OAuth2AuthenticationToken) p;
            return user.getPrincipal().getAttribute("name");
        } else {
            UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) p;
            XUserDetails xUserDetails = (XUserDetails) user.getPrincipal();
            return xUserDetails.getFullName();
        }
    }

}
