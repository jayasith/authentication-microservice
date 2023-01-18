package com.example.authentication.service;

import com.example.authentication.persistance.UserPersistence;
import com.example.authentication.persistance.entity.User;
import com.example.authentication.persistance.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private UserPersistence userPersistence;

    @BeforeEach
    void setUp() {
        userPersistence = new UserPersistence(userRepository);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void userSignUp() {
        User user =User.builder()
                .id(1l)
                .email("test1@Gmail.com")
                .password("test1")
                .username("test1")
                .build();

        userPersistence.saveUser(user);

        Optional<User> result = userRepository.findById(user.getId());

        assertEquals(result.get().getUsername(),user.getUsername());
    }
}