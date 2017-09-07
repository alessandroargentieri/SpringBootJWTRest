package com.example.bytecode.SpringBootJWT.services;

import com.example.bytecode.SpringBootJWT.daos.UserDao;
import com.example.bytecode.SpringBootJWT.entities.User;
import com.example.bytecode.SpringBootJWT.utils.EncryptionUtils;
import com.example.bytecode.SpringBootJWT.utils.JwtUtils;
import com.example.bytecode.SpringBootJWT.utils.UserNotLoggedException;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;



//@RunWith(MockitoJUnitRunner.class)          //to run Mockito
@RunWith(PowerMockRunner.class)
@PrepareForTest({JwtUtils.class})           //powerMock annotations to mock classes containing static methods
public class LoginServiceImplTest {

    @InjectMocks
    LoginServiceImpl loginService;          //System under test (SUT) - Concrete class

    @Mock
    UserDao userDao;                        //first dependency of the SUT to mock

    @Mock
    EncryptionUtils encryptionUtils;        //second dependency of the SUT to mock

    @Mock
    HttpServletRequest request;



    /*
    test on:    LoginServiceImpl#getUserFromDbAndVerifyPassword()
    case:       User found in DB and Password correct
     */
    @Test
    public void userPresentAndPasswordCorrectTest() throws Exception{
        Optional<User> baudo = Optional.of(new User("BDAGPP32E08F205K", "Pippo Baudo","MiPiaceSanRemoEncrypted","conduttore"));
        when(userDao.findById("BDAGPP32E08F205K")).thenReturn(baudo);
        when(encryptionUtils.decrypt("MiPiaceSanRemoEncrypted")).thenReturn("MiPiaceSanRemo");

        assertEquals(loginService.getUserFromDbAndVerifyPassword("Pippo Baudo", "MiPiaceSanRemo"), baudo);
    }



    /*
    test on:    LoginServiceImpl#getUserFromDbAndVerifyPassword()
    case:       User found in DB and Password wrong
     */
    @Test(expected = UserNotLoggedException.class) //to verify an Exception you don't need of an assert
    public void userPresentAndPasswordWrongTest() throws UserNotLoggedException{
        Optional<User> baudo = Optional.of(new User("BDAGPP32E08F205K", "Pippo Baudo","MiPiaceSanRemoEncrypted","conduttore"));
        when(userDao.findById("BDAGPP32E08F205K")).thenReturn(baudo);
        when(encryptionUtils.decrypt("MiPiaceSanRemoEncrypted")).thenReturn("MiPiaceSanRemo");

        loginService.getUserFromDbAndVerifyPassword("Pippo Baudo", "MiPiaceVentimiglia");
    }



    /*
    test on:    LoginServiceImpl#getUserFromDbAndVerifyPassword()
    case:       User not found in DB
     */
    @Test
    public void userAbsentTest() throws UserNotLoggedException{
        Optional<User> empty = Optional.empty();
        when(userDao.findById("BDAGPP32E08F205K")).thenReturn(empty);

        assertEquals(loginService.getUserFromDbAndVerifyPassword("Pippo Baudo", "MiPiaceVentimiglia"), empty);

    }



    /*
    test on:    LoginServiceImpl#createJwt()
    case:       Test of a JWT generation using sample data
     */
    @Test
    public void createJwtTest1() throws Exception {

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date expdate = ft.parse("2040-12-12");

        String jwt = JwtUtils.generateJwt("BDAGPP32E08F205K", expdate, "Pippo Baudo", "conduttore");
        when(JwtUtils.generateJwt("BDAGPP32E08F205K", expdate, "Pippo Baudo", "conduttore")).thenReturn(jwt);

        assertThat(loginService.createJwt("BDAGPP32E08F205K", "Pippo Baudo", "conduttore", expdate), is(jwt));
    }


    /*
    test on:    LoginServiceImpl#createJwt()
    case:       Test of a JWT generation using sample data
     */
    @Test
    public void createJwtTest2() throws Exception {

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date expdate = ft.parse("2040-12-12");

        String jwt = "aaaaaa.bbbbbb.cccccc";
        when(JwtUtils.generateJwt("BDAGPP32E08F205K", expdate, "Pippo Baudo", "conduttore")).thenReturn(jwt);

        assertThat(loginService.createJwt("BDAGPP32E08F205K", "Pippo Baudo", "conduttore", expdate), is(jwt));
    }



    /*
    test on:    LoginServiceImpl#verifyJwtAndGetData()
    case:       Token not present in header request => Launch of UserNotLoggedException
     */
    @Test(expected = UserNotLoggedException.class)  //to verify an Exception you don't need of an assert
    public void tokenNotPresentTest() throws UserNotLoggedException,UnsupportedEncodingException {
        when(JwtUtils.getJwtFromHttpRequest(request)).thenReturn(null);

        loginService.verifyJwtAndGetData(request);
    }



    /*
    test on:    LoginServiceImpl#verifyJwtAndGetData()
    case:       Token with a wrong encoding => Launch of UnsupportedEncodingException
     */
    @Test(expected = UserNotLoggedException.class) //to verify an Exception you don't need of an assert
    public void tokenWithWrongEncodingTest() throws Exception {
        when(JwtUtils.getJwtFromHttpRequest(request)).thenThrow(new UserNotLoggedException("You're not logged!"));
        loginService.verifyJwtAndGetData(request);
    }



    /*
    test on:    LoginServiceImpl#verifyJwtAndGetData()
    case:       Token with an expired date => Launch of ExpiredJwtException
     */
    @Test(expected = ExpiredJwtException.class)  //to verify an Exception you don't need of an assert
    public void tokenExpiredTest() throws Exception {
        when(JwtUtils.getJwtFromHttpRequest(request)).thenThrow(new ExpiredJwtException(null, null, null));

        loginService.verifyJwtAndGetData(request);
    }



    /*
    test on:    LoginServiceImpl#verifyJwtAndGetData()
    case:       Token valid => get user data in a Map
     */
    @Test
    public void tokenValidTest() throws Exception {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = ft.parse("2040-12-12");

        Map<String, Object> baudoData = new HashMap<>();
        baudoData.put("name", "Pippo Baudo");
        baudoData.put("scope", "conduttore");
        baudoData.put("exp_date", date);
        baudoData.put("subject", "BDAGPP32E08F205K");

        String jwt = "aaaaaa.bbbbbb.cccccc";

        when(JwtUtils.getJwtFromHttpRequest(request)).thenReturn(jwt);
        when(JwtUtils.jwt2Map(jwt)).thenReturn(baudoData);

        assertEquals(loginService.verifyJwtAndGetData(request), baudoData);

    }


}