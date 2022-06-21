/**
 * 
 */
package fr.example.bank.services;

import org.springframework.stereotype.Service;

import com.baeldung.openapi.model.Account;

/**
 * @author berenice
 *
 */
@Service
public interface AccountService {

	Account calculateAndSaveNewBalenceInAccount(long amount, int accountId, String operation);

	Account getAccountById(int accountId);

}
