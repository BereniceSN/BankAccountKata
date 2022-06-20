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
import fr.example.bank.repositories.AccountRepository;

/**
 * @author berenice
 *
 */
@DataJpaTest
class AccountRepositoryTest {
	
	private static final String NAME = "testeur";
	private static final long BALANCE = 1000l;
	private static final String NAME_2 = "testeur2";
	private static final long BALANCE_2 = 100l;
	
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
		accountRepository.save(account1);
		AccountBdd account2 = new AccountBdd();
		account2.setBalance(BALANCE_2);
		account2.setName(NAME_2);
		accountRepository.save(account2);
	}

	@Test
	void should_return_an_accountbdd_when_searchingById() {
		
		//given
		//when
		AccountBdd accountBdd = accountRepository.findByName(NAME).get();
		
		//then
		assertEquals(BALANCE,accountBdd.getBalance());
		
	}
	
	@Test
	void should_save_new_balance_in_account() {
		//given
		long newBalance = BALANCE_2;
		
		//when
		AccountBdd accountBdd = accountRepository.findByName(NAME).get();
		accountBdd.setBalance(newBalance);
		accountBdd = accountRepository.save(accountBdd);
		
		//then
		assertEquals(BALANCE_2,accountBdd.getBalance());
		
	}

}
