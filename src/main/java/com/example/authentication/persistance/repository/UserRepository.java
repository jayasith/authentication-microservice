package com.example.authentication.persistance.repository;

import com.example.authentication.persistance.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);

}
