package com.example.st1580.service;

import com.example.st1580.controller.UserController;
import com.example.st1580.controller.dto.UserDto;
import com.example.st1580.document.User;
import com.example.st1580.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService implements UserController {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public Mono<User> register(UserDto user) {
        User.CurrencyType type;
        try {
            type = User.CurrencyType.valueOf(user.getCurrencyType());
        } catch (IllegalArgumentException e) {
            return Mono.error(() -> e);
        }

        User newUser = new User(user.getName(), type);
        return userRepository.save(newUser);
    }
}
