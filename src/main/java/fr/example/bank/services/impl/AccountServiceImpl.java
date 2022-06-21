/**
 * 
 */
package fr.example.bank.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baeldung.openapi.model.Account;
import com.baeldung.openapi.model.Transaction;

import fr.example.bank.entities.AccountBdd;
import fr.example.bank.mappers.AccountMapper;
import fr.example.bank.mappers.TransactionMapper;
import fr.example.bank.repositories.AccountRepository;
import fr.example.bank.repositories.TransactionRepository;
import fr.example.bank.services.AccountService;

/**
 * @author berenice
 *
 */
@Service
public class AccountServiceImpl implements AccountService {
	
	AccountRepository accountRepository;
	
	TransactionRepository transactionRepository;
	
	AccountMapper accountMapper = new AccountMapper();
	
	TransactionMapper transactionMapper = new TransactionMapper();

	public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	AccountBdd saveNewBalenceInAccount(long newBalance,AccountBdd accountBdd) {
			accountBdd.setBalance(newBalance);
		return accountRepository.save(accountBdd);
	}
	
	@Override
	public Account calculateAndSaveNewBalenceInAccount(long amount, int accountId, String operation) {
		Optional<AccountBdd> optionalAccount = accountRepository.findById(accountId);
		AccountBdd reponseBdd = new AccountBdd();
		
		if(optionalAccount.isPresent()) {
			AccountBdd accountBdd = optionalAccount.get();
			reponseBdd = saveNewBalenceInAccount(calculateNewBalance(amount,accountBdd.getBalance(),operation),accountBdd);
		}
		
		// recuperation des transactions liees au compte
		List<Transaction> transactions = transactionRepository.findByAccountId(accountId).stream()
				.map(transactionBdd -> transactionMapper.toTransaction(transactionBdd)).collect(Collectors.toList());
		
		return accountMapper.toAccount(reponseBdd, transactions);
	}
	
	private long calculateNewBalance(long amount, long oldBalence,String operation) {
		switch(operation.toLowerCase()) {
			case "save":
				return oldBalence + amount;
			case "withdraw":
				return oldBalence - amount;
			default:
				return oldBalence;
		}
		
	}
	
	@Override
	public Account getAccountById(int accountId) {
		
		AccountBdd accountBdd = new AccountBdd();
		Optional<AccountBdd> optionalAccount = accountRepository.findById(accountId);
		
		if(optionalAccount.isPresent()) {
			accountBdd = optionalAccount.get();
		}
		
		// recuperation des transactions liees au compte
		List<Transaction> transactions = transactionRepository.findByAccountId(accountId).stream()
				.map(transactionBdd -> transactionMapper.toTransaction(transactionBdd)).collect(Collectors.toList());
		
		return accountMapper.toAccount(accountBdd, transactions);
	}
	
	
	
	
	
	

}
