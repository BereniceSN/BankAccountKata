/**
 * 
 */
package fr.example.bank.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.openapi.model.Transaction;

/**
 * @author berenice
 *
 */
@Service
public interface TransactionService {
	
	
	/**
	 * enregistrement en base de donnees, d'une transaction effectuee
	 * @param amount
	 * @param operation
	 * @param accountId
	 * @return
	 */
	Transaction saveTransaction(long amount, String operation,int accountId);
	
	/**
	 * recuperation de la liste de transactions d'un compte precis a partir de son id de compte
	 * @param accountId
	 * @return
	 */
	List<Transaction> getAllTransactionsByAccountId(int accountId);

}
