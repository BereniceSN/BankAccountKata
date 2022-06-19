/**
 * 
 */
package fr.example.bank.mappers;

import java.util.List;

import com.baeldung.openapi.model.Account;
import com.baeldung.openapi.model.Transaction;

import fr.example.bank.entities.AccountBdd;

/**
 * @author berenice
 *
 */
public class AccountMapper {

	public Account toAccount(AccountBdd accountBdd, List<Transaction> transactions) {
		Account account = new Account();
		account.setName(accountBdd.getName());
		account.setBalance(String.valueOf(accountBdd.getBalance()));
		account.setTransactions(transactions);
		return account;
	}

	public AccountBdd toAccountBdd(Account account) {
		AccountBdd accountBdd = new AccountBdd();
		accountBdd.setBalance(Long.parseLong(account.getBalance()));
		accountBdd.setName(account.getName());
		return accountBdd;
	}
	
	

}
