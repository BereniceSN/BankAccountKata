/**
 * 
 */
package fr.example.bank.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.openapi.model.Account;

import fr.example.bank.entities.AccountBdd;
import fr.example.bank.entities.TransactionBdd;
import fr.example.bank.repositories.AccountRepository;
import fr.example.bank.repositories.TransactionRepository;

/**
 * @author berenice
 *
 */
@RunWith(SpringRunner.class)
class AccountServiceImplTest {
	
	private static final int ACCOUNT_ID = 1;
	private static final String NAME = "testeur";
	private static final long BALANCE = 1000l;
	private static final long NEW_BALANCE=100l;
	private static final long AMOUNT=100l;
	private static final long NEW_BALANCE_2=1100l;
	private static final String OPERATION_SAVE = "SAVE";
	
	private static final int TRANSACTION_ID = 1;
	private static final Date DATE_TRANSACTION = new Date();
	private static final String OPERATION = "SAVE";


	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	private AccountServiceImpl accountService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		accountRepository = Mockito.mock(AccountRepository.class);
		transactionRepository = Mockito.mock(TransactionRepository.class);
		accountService = new AccountServiceImpl(accountRepository,transactionRepository);
	}

	@Test
	void should_save_new_balance_in_account() {
		//given
		Account account = new Account();
		account.setName(NAME);
		account.setBalance(String.valueOf(BALANCE));
		AccountBdd accountBdd= new AccountBdd();
		accountBdd.setId(ACCOUNT_ID);
		accountBdd.setName(NAME);
		accountBdd.setBalance(BALANCE);
		Optional<AccountBdd> optional = Optional.of(accountBdd);
		
		AccountBdd accountBddNewBalance=accountBdd;
		accountBddNewBalance.setBalance(NEW_BALANCE);
		
		//when
		when(accountRepository.findById(ACCOUNT_ID)).thenReturn(optional);
		when(accountRepository.save(accountBddNewBalance)).thenReturn(accountBddNewBalance);

		AccountBdd accountResponse = accountService.saveNewBalenceInAccount(NEW_BALANCE,accountBdd);
		
		//then
		assertEquals(NEW_BALANCE,accountResponse.getBalance());
	}
	
	@Test
	void should_calculate_from_an_amount_of_operation_SAVE_and_save_new_balance_in_account() {
		
		//given
		Account account = new Account();
		account.setName(NAME);
		account.setBalance(String.valueOf(BALANCE));
		AccountBdd accountBdd= new AccountBdd();
		accountBdd.setId(ACCOUNT_ID);
		accountBdd.setName(NAME);
		accountBdd.setBalance(BALANCE);
		Optional<AccountBdd> optional = Optional.of(accountBdd);
		
		AccountBdd accountBddNewBalance=new AccountBdd();
		accountBdd.setId(ACCOUNT_ID);
		accountBdd.setName(NAME);
		accountBddNewBalance.setBalance(NEW_BALANCE_2);
		
		List<TransactionBdd> transactions = new ArrayList<TransactionBdd>();
		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setId(TRANSACTION_ID);
		transactionBdd.setAccountId(ACCOUNT_ID);
		transactionBdd.setAmount(AMOUNT);
		transactionBdd.setDateTransaction(DATE_TRANSACTION);
		transactionBdd.setOperation(OPERATION);
		transactions.add(transactionBdd);
		
		//when
		when(accountRepository.findById(ACCOUNT_ID)).thenReturn(optional);
		when(accountRepository.save(accountBdd)).thenReturn(accountBddNewBalance);
		when(transactionRepository.findByAccountId(ACCOUNT_ID)).thenReturn(transactions);
		Account accountResponse = accountService.calculateAndSaveNewBalenceInAccount(AMOUNT,ACCOUNT_ID,OPERATION_SAVE);
				
		//then
		assertEquals(String.valueOf(NEW_BALANCE_2),accountResponse.getBalance());
	}
	
	@Test
	void should_return_from_an_id_Account_the_account() {
		//given
		AccountBdd accountBdd= new AccountBdd();
		accountBdd.setId(ACCOUNT_ID);
		accountBdd.setName(NAME);
		accountBdd.setBalance(BALANCE);
		Optional<AccountBdd> optional = Optional.of(accountBdd);

		List<TransactionBdd> transactions = new ArrayList<TransactionBdd>();
		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setId(TRANSACTION_ID);
		transactionBdd.setAccountId(ACCOUNT_ID);
		transactionBdd.setAmount(AMOUNT);
		transactionBdd.setDateTransaction(DATE_TRANSACTION);
		transactionBdd.setOperation(OPERATION);
		transactions.add(transactionBdd);
		
		//when
		when(accountRepository.findById(ACCOUNT_ID)).thenReturn(optional);
		when(transactionRepository.findByAccountId(ACCOUNT_ID)).thenReturn(transactions);
		Account account = accountService.getAccountById(accountBdd.getId());
		
		//then
		assertEquals(accountBdd.getName(), account.getName());
		assertEquals(String.valueOf(accountBdd.getBalance()),account.getBalance());
		assertEquals(1,account.getTransactions().size());
	}

}
