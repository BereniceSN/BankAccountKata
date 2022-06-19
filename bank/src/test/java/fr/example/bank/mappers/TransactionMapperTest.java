/**
 * 
 */
package fr.example.bank.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.baeldung.openapi.model.Transaction;
import com.baeldung.openapi.model.Transaction.OperationEnum;

import fr.example.bank.entities.TransactionBdd;
import fr.example.bank.mappers.TransactionMapper;

/**
 * @author berenice
 *
 */
class TransactionMapperTest {
	
	private static final long AMOUNT_100 = 100l;
	private static final Date DATE_TRANSACTION = new Date();
	private static final int ACCOUNT_ID = 1;
	
	private TransactionMapper transactionMapper = new TransactionMapper();

	@Test
	void should_return_from_transactionBdd_a_transactionApi() {
		//given
		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setAmount(AMOUNT_100);
		transactionBdd.setDateTransaction(DATE_TRANSACTION);
		transactionBdd.setOperation(OperationEnum.SAVE.getValue());
		
		//when
		Transaction transaction = transactionMapper.toTransaction(transactionBdd);
		
		//then
		assertEquals(OperationEnum.SAVE,transaction.getOperation());
		assertEquals(DATE_TRANSACTION,transaction.getDate());
		assertEquals(String.valueOf(AMOUNT_100),transaction.getAmount());
		
	}
	
	@Test
	void should_return_from_transaction_a_transactionBdd() {
		//given
		Transaction transaction = new Transaction();
		transaction.setAmount(String.valueOf(AMOUNT_100));
		transaction.setDate(DATE_TRANSACTION);
		transaction.setOperation(OperationEnum.SAVE);
		
		//when
		TransactionBdd transactionBdd = transactionMapper.toTransactionBdd(transaction,ACCOUNT_ID);
		
		//then
		assertEquals(OperationEnum.SAVE.getValue(),transactionBdd.getOperation());
		assertEquals(DATE_TRANSACTION,transactionBdd.getDateTransaction());
		assertEquals(AMOUNT_100,transactionBdd.getAmount());
		assertEquals(ACCOUNT_ID, transactionBdd.getAccountId());
	}

}
