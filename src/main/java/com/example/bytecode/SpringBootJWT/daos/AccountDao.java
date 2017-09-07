package com.example.bytecode.SpringBootJWT.daos;

import com.example.bytecode.SpringBootJWT.entities.Account;
import com.example.bytecode.SpringBootJWT.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * In this case, while defining this Dao interface we're using two approaches:
 * 1. we specific the query in the @Query annotation;
 * 2. we use the name query strategy following specific naming rules to let Spring understand.
 * The concrete class will be created magically and hiddenly by Spring.
 * N.B. We don't annotate this class with @Repository because it's an interface.
 * @Repository it's dedicated to concrete classes.
 */
public interface AccountDao extends JpaRepository<Account, String>{

    @Query(value = "SELECT * FROM accounts WHERE FK_USER=:user", nativeQuery = true)
    List<Account> getAllAccountsPerUser(@Param("user")String user);

    List<Account> findByFkUser(String fkUser);

}
