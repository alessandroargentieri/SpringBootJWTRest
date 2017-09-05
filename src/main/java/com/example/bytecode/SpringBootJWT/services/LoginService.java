package com.example.bytecode.SpringBootJWT.services;

import com.example.bytecode.SpringBootJWT.entities.User;
import com.example.bytecode.SpringBootJWT.utils.UserNotLoggedException;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

public interface LoginService {


    /**
     * this method calls the UserDao to get the user having his/her username
     * @param username
     * @return
     */
    Optional<User> getUserFromDbAndVerifyPassword(String username, String password)throws UserNotLoggedException;

    /**
     * this method gets the credentials of the user and returns the token
     * @return jwt
     */
    String createJwt(String subject, String name, String permission)  throws java.io.UnsupportedEncodingException;

    /**
     * this method gets the Token and returns User data
     * @return Map of elements of the JSON of the token, containing the User data
     */
    Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws java.io.UnsupportedEncodingException, ExpiredJwtException, UserNotLoggedException;

    /**
     * this method returns the User found in the DB given it's username
     * @param username
     * @return
     */
   // User findUserByUsername(String username);
}
