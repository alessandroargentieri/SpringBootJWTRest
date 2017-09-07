package com.example.bytecode.SpringBootJWT.daos;

import com.example.bytecode.SpringBootJWT.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * We're specifing the SQL query in the @Query annotation.
 * Spring will create the concrete class which implements this interface automatically.
 */
public interface OperationDao extends JpaRepository<Operation, String> {

    @Query(value = "SELECT * FROM operations WHERE FK_ACCOUNT1=:account OR FK_ACCOUNT2=:account", nativeQuery = true)
    List<Operation> findAllOperationsByAccount(@Param("account")String account);


}
