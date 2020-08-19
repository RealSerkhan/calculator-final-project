package com.example.calculator.services;

import com.example.calculator.model.Operation;
import com.example.calculator.model.User;

import java.util.Optional;

public interface UserService extends CoreService<User> {


    void addOperation(String email, Operation operation);
    Optional<User> findByEmail(String email);
}
