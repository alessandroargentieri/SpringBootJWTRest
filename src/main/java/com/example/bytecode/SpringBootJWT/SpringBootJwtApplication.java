package com.example.bytecode.SpringBootJWT;

import com.example.bytecode.SpringBootJWT.daos.AccountDao;
import com.example.bytecode.SpringBootJWT.daos.OperationDao;
import com.example.bytecode.SpringBootJWT.daos.UserDao;
import com.example.bytecode.SpringBootJWT.entities.Account;
import com.example.bytecode.SpringBootJWT.entities.Operation;
import com.example.bytecode.SpringBootJWT.entities.User;
import com.example.bytecode.SpringBootJWT.utils.EncryptionUtils;
import com.example.bytecode.SpringBootJWT.utils.ReflectionTestClass;
import com.example.bytecode.SpringBootJWT.utils.ReflectionUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;


@SpringBootApplication
public class SpringBootJwtApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootJwtApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtApplication.class, args);
	}



	@Autowired
	UserDao userDao;

	@Autowired
	AccountDao accountDao;

	@Autowired
	OperationDao operationDao;

	@Autowired
	EncryptionUtils encriptionUtils;

	@Override
	public void run(String... strings) throws Exception {

		/*
		operations to initialize and fill the database to simulate it has an historical set of data
		 */

		String encryptedPwd = encriptionUtils.encrypt("Abba");
		System.out.println("Ecripted pwd into DB: " + encryptedPwd);
		log.info("Ecripted pwd into DB: " + encryptedPwd);
		userDao.save(new User("RGNLSN87H13D761R", "Alessandro Argentieri", encryptedPwd, "user"));

		encryptedPwd = encriptionUtils.encrypt("WeLoveTokyo");
		userDao.save(new User("FRNFBA85M08D761M", "Fabio Fiorenza", encryptedPwd, "user"));

		encryptedPwd = encriptionUtils.encrypt("Melograno");
		userDao.save(new User("DSTLCU89R52D761R", "Lucia Distante", encryptedPwd, "user"));

		accountDao.save(new Account("cn4563df3", "RGNLSN87H13D761R", 3000.00));
		accountDao.save(new Account("cn7256su9", "RGNLSN87H13D761R", 4000.00));
		accountDao.save(new Account("cn6396dr7", "FRNFBA85M08D761M", 7000.00));
		accountDao.save(new Account("cn2759ds4", "DSTLCU89R52D761R", 2000.00));
		accountDao.save(new Account("cn2874da2", "DSTLCU89R52D761R", 8000.00));

		operationDao.save(new Operation("3452",new Date(),"Bonifico bancario", 100.00, "cn4563df3","cn4563df3"));
		operationDao.save(new Operation("3453",new Date(),"Pagamento tasse", -100.00, "cn4563df3","cn4563df3"));
		operationDao.save(new Operation("3454",new Date(),"Postagiro", 230.00, "cn4563df3","cn2759ds4"));
		operationDao.save(new Operation("3455",new Date(),"Vaglia postale", 172.00, "cn4563df3","cn4563df3"));
		operationDao.save(new Operation("3456",new Date(),"Acquisto azioni", -3400.00, "cn2759ds4",""));
		operationDao.save(new Operation("3457",new Date(),"Vendita azione", 100.00, "cn4563df3",""));
		operationDao.save(new Operation("3458",new Date(),"Prelevamento", -100.00, "cn4563df3",""));
		operationDao.save(new Operation("3459",new Date(),"Deposito", 1100.00, "cn4563df3",""));
		operationDao.save(new Operation("3460",new Date(),"Bonifico bancario", 100.00, "cn2874da2","cn4563df3"));
		operationDao.save(new Operation("3461",new Date(),"Bonifico bancario", 100.00, "cn4563df3","cn2874da2"));
		operationDao.save(new Operation("3462",new Date(),"Bonifico bancario", 100.00, "cn4563df3","cn4563df3"));
		operationDao.save(new Operation("3463",new Date(),"Postagiro", 230.00, "cn7256su9","cn2759ds4"));
		operationDao.save(new Operation("3464",new Date(),"Vaglia postale", 172.00, "cn4563df3","cn7256su9"));
		operationDao.save(new Operation("3465",new Date(),"Acquisto azioni", -3400.00, "cn7256su9",""));


		ReflectionTestClass myinstance = new ReflectionTestClass();
		String privateFieldValue = (String) ReflectionUtils.getPrivateField(myinstance, "myField");
		log.info("privateField (1): " + privateFieldValue);

		ReflectionUtils.setPrivateField(myinstance, "myField", "Hello!");
		privateFieldValue = (String) ReflectionUtils.getPrivateField(myinstance, "myField");
		log.info("privateField (2): " + privateFieldValue);

	}


	/*
	don't forget @SpringBootApplication is also a @Configuration file in which you can declare @Beans to be injected anywhere
	 */
	@Bean
	public BasicTextEncryptor textEncryptor(){
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword("mySecretEncriptionKeyBlaBla1234");
		return textEncryptor;
	}


}
