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
	
	int account1Id = 1;
	private static final String NAME = "testeur";
	private static final long BALANCE = 1000l;
	private static final Date DATE_TRANSACTION = new Date();
	private static final String OPERATION = "SAVE";
	private static final long AMOUNT=100l;
	
	int account2Id = 2;
	private static final String NAME_2 = "testeur2";
	private static final long BALANCE_2 = 100l;
	private static final String OPERATION_2 = "SAVE";
	private static final long AMOUNT_2=20l;
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
		AccountBdd account1 = new AccountBdd();
		account1.setBalance(BALANCE);
		account1.setName(NAME);
		account1 = accountRepository.save(account1);
		account1Id = account1.getId();
		AccountBdd account2 = new AccountBdd();
		account2.setBalance(BALANCE_2);
		account2.setName(NAME_2);
		account2 = accountRepository.save(account2);
		account2Id = account2.getId();

		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setAccountId(account1Id);
		transactionBdd.setAmount(AMOUNT);
		transactionBdd.setDateTransaction(DATE_TRANSACTION);
		transactionBdd.setOperation(OPERATION);
		transactionRepository.save(transactionBdd);
		
		TransactionBdd transactionBdd2 = new TransactionBdd();
		transactionBdd2.setAccountId(account2Id);
		transactionBdd2.setAmount(AMOUNT_2);
		transactionBdd2.setDateTransaction(DATE_TRANSACTION);
		transactionBdd2.setOperation(OPERATION_2);
		transactionRepository.save(transactionBdd2);
		
		TransactionBdd transactionBdd3 = new TransactionBdd();
		transactionBdd3.setAccountId(account1Id);
		transactionBdd3.setAmount(AMOUNT_2);
		transactionBdd3.setDateTransaction(DATE_TRANSACTION);
		transactionBdd3.setOperation(OPERATION_3);
		transactionRepository.save(transactionBdd3);
	}

	@Test
	void should_return_transaction_when_searchingByAccountId() {
		//given
		//when
		List<TransactionBdd> transactions = transactionRepository.findByAccountId(account2Id);
		
		//then
		assertEquals(AMOUNT_2,transactions.get(0).getAmount());
	}
	
	@Test
	void should_return_all_transactions_when_searchingByAccountId() {
		
		//given
		//when
		List<TransactionBdd> transactions = transactionRepository.findByAccountId(account1Id);
		
		//then
		assertNotNull(transactions);
		assertEquals(2,transactions.size());
		assertEquals(OPERATION_2,transactions.get(0).getOperation());
		assertEquals(AMOUNT,transactions.get(0).getAmount());
		assertEquals(OPERATION_3,transactions.get(1).getOperation());
		assertEquals(AMOUNT_2,transactions.get(1).getAmount());
		
	}

}
