/**
 * 
 */
package fr.example.bank.controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.openapi.model.Account;
import com.baeldung.openapi.model.Transaction;
import com.baeldung.openapi.model.Transaction.OperationEnum;

import fr.example.bank.services.AccountService;
import fr.example.bank.services.TransactionService;

/**
 * @author berenice
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class ManageAccountControllerTest {
	
	@Mock
	AccountService accountService;
	
	@Mock
	TransactionService transactionService;
	
	ManageAccountController manageAccountController;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final int ACCOUNT_ID = 1;
	private static final long AMOUNT = 100l;
	private static final String NAME = "testeur";
	private static final long BALANCE = 100l;
	private static final String OPERATION_SAVE="SAVE";
	private static final String OPERATION_WITHDRAW="WITHDRAW";
	private static final Date DATE_TRANSACTION = new Date();
	
	private final String GET_TRANSACTION_API="/api/account/"+ACCOUNT_ID;
	private static final String GET_TRANSACTION_API_WITH_ACCOUNT_ID_ERROR = "/api/account/a";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		accountService = Mockito.mock(AccountService.class);
		transactionService = Mockito.mock(TransactionService.class);
		manageAccountController = new ManageAccountController(accountService,transactionService);
	}

	@Test
	void should_return_a_transaction() throws Exception {
		//given
		Account account = new Account();
		account.setName(NAME);
		account.setBalance(String.valueOf(BALANCE));
		
		//when, then
		when(accountService.getAccountById(ACCOUNT_ID)).thenReturn(account);
		mockMvc.perform(get(GET_TRANSACTION_API)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	void should_throw_a_badRequestError_when_id_not_number() throws Exception {
		
		//given
		Account account = new Account();
		account.setName(NAME);
		account.setBalance(String.valueOf(BALANCE));
		
		//when, then
		mockMvc.perform(get(GET_TRANSACTION_API_WITH_ACCOUNT_ID_ERROR)).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	void should_return_liste_transactions_on_an_account_id() throws Exception {
		
		//given
		List<Transaction> reponse = new ArrayList<Transaction>();
		Transaction transaction = new Transaction();
		transaction.setAmount(String.valueOf(AMOUNT));
		transaction.setDate(DATE_TRANSACTION);
		transaction.setOperation(OperationEnum.WITHDRAW);
		reponse.add(transaction);
		
		//when, then
		when(transactionService.getAllTransactionsByAccountId(ACCOUNT_ID)).thenReturn(reponse);
		ResponseEntity<List<Transaction>> transactions = manageAccountController
				.getAllAccountTransactions(String.valueOf(ACCOUNT_ID));
		
		//then
		assertEquals(1, transactions.getBody().size());
		assertEquals(OperationEnum.WITHDRAW,transactions.getBody().get(0).getOperation());
		
	}
	
	@Test
	void should_save_new_balence_on_an_account_when_save_operation() throws Exception {
		//given
		Transaction transaction = new Transaction();
		transaction.setAmount(String.valueOf(AMOUNT));
		transaction.setDate(DATE_TRANSACTION);
		transaction.setOperation(OperationEnum.SAVE);
		
		Account account = new Account();
		account.setName(NAME);
		account.setBalance(String.valueOf(BALANCE+AMOUNT));
		
		//when
		when(transactionService.saveTransaction(AMOUNT, OPERATION_SAVE, ACCOUNT_ID)).thenReturn(transaction);
		when(accountService.calculateAndSaveNewBalenceInAccount(AMOUNT, ACCOUNT_ID, OPERATION_SAVE)).thenReturn(account);
		ResponseEntity<Boolean> save = manageAccountController.saveInAccount(String.valueOf(ACCOUNT_ID), BigDecimal.valueOf(AMOUNT));
		
		//then
		assertTrue(save.getBody());
	}
	
	@Test
	void should_withdraw_new_balence_on_an_account_when_withdraw_operation() throws Exception {
		//given
		Transaction transaction = new Transaction();
		transaction.setAmount(String.valueOf(AMOUNT));
		transaction.setDate(DATE_TRANSACTION);
		transaction.setOperation(OperationEnum.WITHDRAW);
		
		Account account = new Account();
		account.setName(NAME);
		account.setBalance(String.valueOf(BALANCE-AMOUNT));
		
		//when
		when(transactionService.saveTransaction(AMOUNT, OPERATION_WITHDRAW, ACCOUNT_ID)).thenReturn(transaction);
		when(accountService.calculateAndSaveNewBalenceInAccount(AMOUNT, ACCOUNT_ID, OPERATION_WITHDRAW)).thenReturn(account);
		ResponseEntity<Boolean> withdraw = manageAccountController.withdrawFromAccount(String.valueOf(ACCOUNT_ID), BigDecimal.valueOf(AMOUNT));
		
		//then
		assertTrue(withdraw.getBody());
	}

}
