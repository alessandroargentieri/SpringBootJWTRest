package com.example.bytecode.SpringBootJWT.services;

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
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service("loginService")
public class LoginServiceImpl implements LoginService{

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Autowired
    EncryptionUtils encryptionUtils;


    @Override
    public Optional<User> getUserFromDbAndVerifyPassword(String id, String password) throws UserNotLoggedException {
        Optional<User> userr = userDao.findById(id);                  //verify the presence into the database
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
    public String createJwt(String subject, String name, String permission, Date datenow)  throws java.io.UnsupportedEncodingException{
       // Date expirationDate = new Date(System.currentTimeMillis() + 300000);

        Date expDate = datenow;
        expDate.setTime(datenow.getTime()+(300*1000));  //expiration time = 30 minutes

        log.info("Creation JWT. Date now: " + new Date().getTime());
        log.info("Creation JWT. Date expiration: " + expDate.getTime());

        String token = JwtUtils.generateJwt(subject, expDate, name, permission);
        return token;

    }



    @Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws java.io.UnsupportedEncodingException, ExpiredJwtException, UserNotLoggedException{
        String jwt = JwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("Authentication token not found in the request");
        }
        Map<String, Object> userData = JwtUtils.jwt2Map(jwt);
        log.info("verify JWT: NOW: " + new Date());
        log.info("verify JWT: EXP: " + (Date) userData.get("exp_date"));
        return userData;
    }



}
