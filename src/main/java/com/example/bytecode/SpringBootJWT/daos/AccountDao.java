package com.example.bytecode.SpringBootJWT.daos;

import com.example.bytecode.SpringBootJWT.entities.Account;
import com.example.bytecode.SpringBootJWT.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountDao extends JpaRepository<Account, String>{

    @Query(value = "SELECT * FROM accounts WHERE FK_USER=:user", nativeQuery = true)
    List<Account> getAllAccountsPerUser(@Param("user")String user);

    List<Account> findByFkUser(String fkUser);

}
