/**
 * 
 */
package fr.example.bank.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baeldung.openapi.model.Transaction;

import fr.example.bank.entities.TransactionBdd;
import fr.example.bank.mappers.TransactionMapper;
import fr.example.bank.repositories.TransactionRepository;
import fr.example.bank.services.TransactionService;

/**
 * @author berenice
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	
	TransactionRepository transactionRepository;
	
	TransactionMapper transactionMapper = new TransactionMapper();

	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository=transactionRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Transaction saveTransaction(long amount, String operation,int accountId) {
		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setAccountId(accountId);
		transactionBdd.setAmount(amount);
		transactionBdd.setOperation(operation);
		transactionBdd.setDateTransaction(new Date());
		TransactionBdd reponse = transactionRepository.save(transactionBdd);
		return transactionMapper.toTransaction(reponse);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Transaction> getAllTransactionsByAccountId(int accountId) {
		List<TransactionBdd> transactionsBdd = transactionRepository.findByAccountId(accountId);
		if(transactionsBdd != null && !transactionsBdd.isEmpty()) {
			return transactionsBdd.stream()
					.map(transactionBdd -> transactionMapper.toTransaction(transactionBdd)).collect(Collectors.toList());
		}
		return new ArrayList<>();
		
	}

}
