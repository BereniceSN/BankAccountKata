/**
 * 
 */
package fr.example.bank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.openapi.model.Transaction;
import com.baeldung.openapi.model.Transaction.OperationEnum;

import fr.example.bank.entities.TransactionBdd;
import fr.example.bank.repositories.TransactionRepository;
import fr.example.bank.services.impl.TransactionServiceImpl;

/**
 * @author berenice
 *
 */
@RunWith(SpringRunner.class)
class TransactionServiceTest {
	
	private static final int ACCOUNT_ID = 1;
	private static final int TRANSACTION_ID = 1;
	private static final Date DATE_TRANSACTION = new Date();
	private static final String OPERATION = "SAVE";
	private static final String OPERATION_WITHDRAW = "WITHDRAW";

	private static final long AMOUNT=100l;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	private TransactionService transactionService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		transactionRepository = Mockito.mock(TransactionRepository.class);
		transactionService = new TransactionServiceImpl(transactionRepository);
	}

	@Test
	void should_save_a_transaction() {
		
		//given
		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setId(TRANSACTION_ID);
		transactionBdd.setAccountId(ACCOUNT_ID);
		transactionBdd.setAmount(AMOUNT);
		transactionBdd.setDateTransaction(DATE_TRANSACTION);
		transactionBdd.setOperation(OPERATION);
		
		//when
		when(transactionRepository.save(any(TransactionBdd.class))).thenReturn(transactionBdd);
		Transaction transaction = transactionService.saveTransaction(AMOUNT,OPERATION,ACCOUNT_ID);
		
		//then
		assertEquals(String.valueOf(AMOUNT),transaction.getAmount());
		assertEquals(OperationEnum.SAVE, transaction.getOperation());
	}
	
	@Test
	void should_return_liste_transaction_of_an_account_id() {
		//given
		List<TransactionBdd> transactions = new ArrayList<TransactionBdd>();

		TransactionBdd transactionBdd = new TransactionBdd();
		transactionBdd.setId(TRANSACTION_ID);
		transactionBdd.setAccountId(ACCOUNT_ID);
		transactionBdd.setAmount(AMOUNT);
		transactionBdd.setDateTransaction(DATE_TRANSACTION);
		transactionBdd.setOperation(OPERATION_WITHDRAW);
		transactions.add(transactionBdd);
		
		//when
		when(transactionRepository.findByAccountId(ACCOUNT_ID)).thenReturn(transactions);
		List<Transaction> reponse = transactionService.getAllTransactionsByAccountId(ACCOUNT_ID);
		
		//then
		assertEquals(1,reponse.size());
		assertEquals(OperationEnum.WITHDRAW, reponse.get(0).getOperation());
	}

}
