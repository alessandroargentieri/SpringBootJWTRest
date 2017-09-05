package com.example.bytecode.SpringBootJWT.services;

import com.example.bytecode.SpringBootJWT.daos.AccountDao;
import com.example.bytecode.SpringBootJWT.daos.OperationDao;
import com.example.bytecode.SpringBootJWT.entities.Account;
import com.example.bytecode.SpringBootJWT.entities.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional                  //because it has access to the database
@Service
public class OperationServiceImpl implements OperationService{


    @Autowired
    AccountDao accountDao;

    @Autowired
    OperationDao operationDao;


    @Override
    public List<Operation> getAllOperationPerAccount(String accountId){
        return operationDao.findAllOperationsByAccount(accountId);
    }

    @Override
    public List<Account> getAllAccountsPerUser(String userId){
        return accountDao.getAllAccountsPerUser(userId);
    }

    @Override
    public Operation saveOperation(Operation operation){
        return operationDao.save(operation);
    }

}
