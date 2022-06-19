/**
 * 
 */
package fr.example.bank.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import fr.example.bank.entities.AccountBdd;
import fr.example.bank.entities.TransactionBdd;

/**
 * @author berenice
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
class TransactionRepositoryTest {
	
	private static final int ACCOUNT_ID = 1;
	private static final String NAME = "testeur";
	private static final long BALANCE = 1000l;
	private static final int TRANSACTION_ID = 1;
	private static final Date DATE_TRANSACTION = new Date();
	private static final String OPERATION = "SAVE";
	private static final long AMOUNT=100l;
	
	private static final int ACCOUNT_ID_2 = 2;
	private static final String NAME_2 = "testeur2";
	private static final long BALANCE_2 = 100l;
	private static final int TRANSACTION_ID_2 = 2;
	private static final String OPERATION_2 = "SAVE";
	private static final long AMOUNT_2=20l;
	private static final int TRANSACTION_ID_3 = 3;
	private static final String OPERATION_3 = "WITHDRAW";

	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		accountRepository.deleteAll();
		AccountBdd account1 = new AccountBdd();
		account1.setId(ACCOUNT_ID);
		account1.setBalance(BALANCE);
		account1.setName(NAME);
		accountRepository.save(account1);
		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setId(TRANSACTION_ID);
		transactionBdd.setAccountId(ACCOUNT_ID);
		transactionBdd.setAmount(AMOUNT);
		transactionBdd.setDateTransaction(DATE_TRANSACTION);
		transactionBdd.setOperation(OPERATION);
		transactionRepository.save(transactionBdd);
		
		AccountBdd account2 = new AccountBdd();
		account2.setId(ACCOUNT_ID_2);
		account2.setBalance(BALANCE_2);
		account2.setName(NAME_2);
		accountRepository.save(account2);
		TransactionBdd transactionBdd2 = new TransactionBdd();
		transactionBdd2.setId(TRANSACTION_ID_2);
		transactionBdd2.setAccountId(ACCOUNT_ID_2);
		transactionBdd2.setAmount(AMOUNT_2);
		transactionBdd2.setDateTransaction(DATE_TRANSACTION);
		transactionBdd2.setOperation(OPERATION_2);
		transactionRepository.save(transactionBdd2);
		
		TransactionBdd transactionBdd3 = new TransactionBdd();
		transactionBdd3.setId(TRANSACTION_ID_3);
		transactionBdd3.setAccountId(ACCOUNT_ID);
		transactionBdd3.setAmount(AMOUNT_2);
		transactionBdd3.setDateTransaction(DATE_TRANSACTION);
		transactionBdd3.setOperation(OPERATION_3);
		transactionRepository.save(transactionBdd3);
	}

	@Test
	void should_return_transaction_when_searchingById() {
		//given
		int transactionId = TRANSACTION_ID;
		
		//when
		TransactionBdd transactionBdd = transactionRepository.findById(transactionId).get();
		
		//then
		assertEquals(AMOUNT,transactionBdd.getAmount());
		assertEquals(ACCOUNT_ID,transactionBdd.getAccountId());
	}
	
	@Test
	void should_return_all_transactions_when_searchingByAccountId() {
		
		//given
		int accountId = ACCOUNT_ID;
		
		//when
		List<TransactionBdd> transactions = transactionRepository.findByAccountId(accountId);
		
		//then
		assertNotNull(transactions);
		assertEquals(2,transactions.size());
		assertEquals(OPERATION_2,transactions.get(0).getOperation());
		assertEquals(AMOUNT,transactions.get(0).getAmount());
		assertEquals(OPERATION_3,transactions.get(1).getOperation());
		assertEquals(AMOUNT_2,transactions.get(1).getAmount());
		
	}

}
