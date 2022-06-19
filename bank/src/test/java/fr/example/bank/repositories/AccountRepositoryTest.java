/**
 * 
 */
package fr.example.bank.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fr.example.bank.entities.AccountBdd;

/**
 * @author berenice
 *
 */
@DataJpaTest
class AccountRepositoryTest {
	
	private static final int ACCOUNT_ID = 1;
	private static final String NAME = "testeur";
	private static final long BALANCE = 1000l;
	private static final int ACCOUNT_ID_2 = 2;
	private static final String NAME_2 = "testeur2";
	private static final long BALANCE_2 = 100l;
	
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
		AccountBdd account2 = new AccountBdd();
		account2.setId(ACCOUNT_ID_2);
		account2.setBalance(BALANCE_2);
		account2.setName(NAME_2);
		accountRepository.save(account2);
	}

	@Test
	void should_return_an_accountbdd_when_searchingById() {
		
		//given
		int accountId = ACCOUNT_ID;
		
		//when
		AccountBdd accountBdd = accountRepository.findById(accountId).get();
		
		//then
		assertEquals(NAME,accountBdd.getName());
		assertEquals(BALANCE,accountBdd.getBalance());
		
	}
	
	@Test
	void should_save_new_balance_in_account() {
		//given
		long newBalance = BALANCE_2;
		
		//when
		AccountBdd accountBdd = accountRepository.findById(ACCOUNT_ID).get();
		accountBdd.setBalance(newBalance);
		accountBdd = accountRepository.save(accountBdd);
		
		//then
		assertEquals(NAME,accountBdd.getName());
		assertEquals(BALANCE_2,accountBdd.getBalance());
		
	}

}
