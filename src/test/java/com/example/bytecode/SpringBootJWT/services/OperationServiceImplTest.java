package com.example.bytecode.SpringBootJWT.services;

import com.example.bytecode.SpringBootJWT.daos.AccountDao;
import com.example.bytecode.SpringBootJWT.daos.OperationDao;
import com.example.bytecode.SpringBootJWT.entities.Account;
import com.example.bytecode.SpringBootJWT.entities.Operation;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.powermock.api.mockito.PowerMockito.when;



public class OperationServiceImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();     //alternative way to run the test class with Mockito (must be public)

    @InjectMocks
    OperationServiceImpl operationService;      //concrete class under test (SUT)

    @Mock
    AccountDao accountDao;                      //dependency to be mocked  and injected into the SUT

    @Mock
    OperationDao operationDao;                  //dependency to be mocked and injected into the SUT



    /**
     * test on:             OperationServiceImpl#findAllOperationsByAccount
     * @throws Exception
     */
    @Test
    public void getAllOperationPerAccountTest() throws Exception {
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation("3453",new Date(),"Pagamento tasse", -100.00, "cn4563df3","cn4563df3"));
        operations.add(new Operation("3454",new Date(),"Postagiro", 230.00, "cn4563df3","cn2759ds4"));
        operations.add(new Operation("3452",new Date(),"Bonifico bancario", 100.00, "cn4563df3","cn4563df3"));

        when(operationDao.findAllOperationsByAccount("cn4563df3")).thenReturn(operations);

        assertThat(operationService.getAllOperationPerAccount("cn4563df3"), is(operations));
    }



    /**
     * test on:             OperationServiceImpl#getAllAccountsPerUser
     * @throws Exception
     */
    @Test
    public void getAllAccountsPerUserTest() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("cn4563df3", "RGNLSN87H13D761R", 3000.00));
        accounts.add(new Account("cn8263ye9", "RGNLSN87H13D761R", 2540.00));

        when(accountDao.getAllAccountsPerUser("RGNLSN87H13D761R")).thenReturn(accounts);

        assertThat(operationService.getAllAccountsPerUser("RGNLSN87H13D761R"), is(accounts));
    }



    /**
     * test on:             OperationServiceImpl#saveOperation
     * @throws Exception
     */
    @Test
    public void saveOperationTest() throws Exception {
        Operation operation = new Operation();

        when(operationDao.save(operation)).thenReturn(operation);

        assertThat(operationService.saveOperation(operation), is(operation));

    }

}