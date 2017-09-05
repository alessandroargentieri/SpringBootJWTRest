package com.example.bytecode.SpringBootJWT.services;

import com.example.bytecode.SpringBootJWT.entities.Account;
import com.example.bytecode.SpringBootJWT.entities.Operation;

import java.util.List;

public interface OperationService {

    /**
     * this method returns a list of Operations given the Account ID
     * @param accountId
     * @return list of Operations
     */
    List<Operation> getAllOperationPerAccount(String accountId);

    /**
     * this method returns a list of Account given the User ID
     * @param userId
     * @return
     */
    List<Account> getAllAccountsPerUser(String userId);

    /**
     * this method gives the Operation back if it was saved successfully
     * @param operation
     * @return
     */
    Operation saveOperation(Operation operation);
}
