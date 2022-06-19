/**
 * 
 */
package fr.example.bank.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.openapi.api.ApiApi;
import com.baeldung.openapi.model.Account;
import com.baeldung.openapi.model.Transaction;
import com.baeldung.openapi.model.Transaction.OperationEnum;

import fr.example.bank.services.AccountService;
import fr.example.bank.services.TransactionService;

/**
 * @author berenice
 *
 */

@RestController
public class ManageAccountController implements ApiApi {
	
	private AccountService accountService;
	
	private TransactionService transactionService;
	
	public ManageAccountController(AccountService accountService, TransactionService transactionService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Account> getAccount(String idAccount) {
		
		int idAccountInt=0;
		try {
			idAccountInt = Integer.valueOf(idAccount);
		} catch (NumberFormatException ex) {
			return new ResponseEntity<>(new Account(),HttpStatus.BAD_REQUEST);
		}
		
		Account account = accountService.getAccountById(idAccountInt);
		
		if(account != null) {
			return new ResponseEntity<>(account,HttpStatus.OK);
		}
		return new ResponseEntity<>(new Account(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<List<Transaction>> getAllAccountTransactions(String idAccount) {
		
		int idAccountInt=0;
		try {
			idAccountInt = Integer.valueOf(idAccount);
		} catch (NumberFormatException ex) {
			return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}
		
		List<Transaction> transactions = transactionService.getAllTransactionsByAccountId(idAccountInt);
		
		if(transactions != null && !transactions.isEmpty()) {
			return new ResponseEntity<>(transactions,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Boolean> saveInAccount(String idAccount, BigDecimal amount) {
		
		int idAccountInt=0;
		try {
			idAccountInt = Integer.valueOf(idAccount);
		} catch (NumberFormatException ex) {
			return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
		}
		
		Transaction transaction = transactionService.saveTransaction(amount.longValue(),  OperationEnum.SAVE.getValue(), idAccountInt);
		if(transaction != null) {
			Account account = accountService.calculateAndSaveNewBalenceInAccount(amount.longValue(), idAccountInt, OperationEnum.SAVE.getValue());
			if(account != null) {
				return new ResponseEntity<>(true,HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Boolean> withdrawFromAccount(String idAccount, BigDecimal amount) {
		
		int idAccountInt=0;
		try {
			idAccountInt = Integer.valueOf(idAccount);
		} catch (NumberFormatException ex) {
			return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
		}
		
		Transaction transaction = transactionService.saveTransaction(amount.longValue(),  OperationEnum.WITHDRAW.getValue(), idAccountInt);
		if(transaction != null) {
			Account account = accountService.calculateAndSaveNewBalenceInAccount(amount.longValue(), idAccountInt, OperationEnum.WITHDRAW.getValue());
			if(account != null) {
				return new ResponseEntity<>(true,HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
