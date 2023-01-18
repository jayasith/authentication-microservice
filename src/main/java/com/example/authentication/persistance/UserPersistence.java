package com.example.authentication.persistance;

import com.example.authentication.persistance.entity.User;
import com.example.authentication.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class UserPersistence {

    private final UserRepository userRepository;
    @Autowired
    public UserPersistence(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
