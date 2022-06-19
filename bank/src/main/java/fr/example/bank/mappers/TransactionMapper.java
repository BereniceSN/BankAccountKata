/**
 * 
 */
package fr.example.bank.mappers;

import com.baeldung.openapi.model.Transaction;
import com.baeldung.openapi.model.Transaction.OperationEnum;

import fr.example.bank.entities.TransactionBdd;

/**
 * @author berenice
 *
 */
public class TransactionMapper {

	public Transaction toTransaction(TransactionBdd transactionBdd) {
		Transaction transaction = new Transaction();
		transaction.setAmount(String.valueOf(transactionBdd.getAmount()));
		transaction.setDate(transactionBdd.getDateTransaction());
		switch(transactionBdd.getOperation().toLowerCase()) {
			case "save":
				transaction.setOperation(OperationEnum.SAVE);
				break;
			case "withdraw":
				transaction.setOperation(OperationEnum.WITHDRAW);
				break;
			default:
				break;
		}
		return transaction;
	}

	public TransactionBdd toTransactionBdd(Transaction transaction, int accountId) {
		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setAccountId(accountId);
		transactionBdd.setAmount(Long.parseLong(transaction.getAmount()));
		transactionBdd.setDateTransaction(transaction.getDate());
		transactionBdd.setOperation(transaction.getOperation().getValue());
		return transactionBdd;
	}

}
