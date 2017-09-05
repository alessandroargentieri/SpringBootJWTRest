package com.example.bytecode.SpringBootJWT.services;

import com.example.bytecode.SpringBootJWT.SpringBootJwtApplication;
import com.example.bytecode.SpringBootJWT.daos.UserDao;
import com.example.bytecode.SpringBootJWT.entities.User;
import com.example.bytecode.SpringBootJWT.utils.EncryptionUtils;
import com.example.bytecode.SpringBootJWT.utils.JwtUtils;
import com.example.bytecode.SpringBootJWT.utils.UserNotLoggedException;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Autowired
    EncryptionUtils encryptionUtils;

    @Autowired
    JwtUtils jwtUtils;



    @Override
    public Optional<User> getUserFromDbAndVerifyPassword(String username, String password) throws UserNotLoggedException {

        Optional<User> userr = userDao.findByUsername(username);      //verify the presence into the database
        if(userr.isPresent()){
            User user = userr.get();                                  //get the User from the optional got from the DB
            if(encryptionUtils.decrypt(user.getPassword()).equals(password)){
                log.info("Username and Password verified. ");
            }else{
                log.info("Username verified, password not. ");
                throw new UserNotLoggedException("User not correctly logged in!");
            }
        }
        return userr;
    }


    @Override
    public String createJwt(String subject, String name, String permission)  throws java.io.UnsupportedEncodingException{
        Date date = new Date();

        Date expirationDate = new Date(System.currentTimeMillis() + 300000);
        date.setTime(date.getTime()+(300*1000));  //expiration time = 30 minutes

        log.info("Creation JWT. Date now: " + new Date().getTime());
        log.info("Creation JWT. Date exp1: " + date.getTime());
        log.info("Creation JWT. Date exp2: " + expirationDate.getTime());


        String token = jwtUtils.generateJwt(subject, expirationDate, name, permission);
        return token;
    }

    @Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws java.io.UnsupportedEncodingException, ExpiredJwtException, UserNotLoggedException{
        String jwt = JwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("Authentication token not found in the request");
        }
        Map<String, Object> userData = jwtUtils.jwt2Map(jwt);
        log.info("verify JWT: NOW: " + new Date());
        log.info("verify JWT: EXP: " + (Date) userData.get("exp_date"));
        return userData;
    }

}
