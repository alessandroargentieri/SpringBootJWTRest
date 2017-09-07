package com.example.bytecode.SpringBootJWT.daos;

import com.example.bytecode.SpringBootJWT.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * in this case we're using the name query strategy.
 * It means we're using precise rules in forming the name of the methods,
 * so Spring, when will implement itself (magically e hiddenly) the concrete class,
 * will understand what we exacly want from this Dao.
 */
public interface UserDao extends JpaRepository<User, String>{

    Optional<User> findById(String id);
}

