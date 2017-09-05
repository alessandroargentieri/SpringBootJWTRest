package com.example.bytecode.SpringBootJWT.daos;

import com.example.bytecode.SpringBootJWT.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String>{

    Optional<User> findByUsername(String username);
}

