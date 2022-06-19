package fr.example.bank.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.openapi.model.Account;
import com.baeldung.openapi.model.Transaction;
import com.baeldung.openapi.model.Transaction.OperationEnum;

import fr.example.bank.entities.AccountBdd;
import fr.example.bank.mappers.AccountMapper;

/**
 * @author berenice
 *
 */
class AccountMapperTest {
	
	private static final String NAME = "testeur";
	private static final long BALANCE = 1000l;
	private static final long AMOUNT_100 = 100l;
	private static final long AMOUNT_200 = 200l;
	private static final Date DATE_TRANSACTION = new Date();

	private AccountMapper accountMapper = new AccountMapper();


	@Test
	void should_return_from_accountBdd_an_accountApi() {
		//given
		AccountBdd accountBdd= new AccountBdd();
		accountBdd.setName(NAME);
		accountBdd.setBalance(BALANCE);
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setAmount(String.valueOf(AMOUNT_100));
		transaction.setOperation(OperationEnum.SAVE);
		transaction.setDate(DATE_TRANSACTION);
		transactions.add(transaction);
		Transaction transaction2 = new Transaction();
		transaction2.setAmount(String.valueOf(AMOUNT_200));
		transaction2.setOperation(OperationEnum.WITHDRAW);
		transaction2.setDate(DATE_TRANSACTION);
		transactions.add(transaction2);
		// when
		Account account = accountMapper.toAccount(accountBdd,transactions);
		
		//then
		assertEquals(NAME, account.getName());
		assertEquals(String.valueOf(BALANCE), account.getBalance());
		assertEquals(2, account.getTransactions().size());
		assertEquals(String.valueOf(AMOUNT_100), account.getTransactions().get(0).getAmount());
		assertEquals(OperationEnum.WITHDRAW,account.getTransactions().get(1).getOperation());
	}
	
	@Test
	void should_return_from_account_an_accountBdd() {
		//given
		Account account = new Account();
		account.setName(NAME);
		account.setBalance(String.valueOf(BALANCE));
		
		//when
		AccountBdd accountBdd = accountMapper.toAccountBdd(account);
		
		//then
		assertEquals(NAME, accountBdd.getName());
		assertEquals(BALANCE, accountBdd.getBalance());	
	}

}
