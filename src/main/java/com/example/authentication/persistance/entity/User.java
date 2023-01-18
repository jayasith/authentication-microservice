package com.example.authentication.persistance.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Document(collation = "user")
public class User {

    @Id
    private long id;
    private String username;
    private String email;
    private String password;

}
