/**
 * 
 */
package fr.example.bank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.example.bank.entities.TransactionBdd;

/**
 * @author berenice
 *
 */
public interface TransactionRepository extends JpaRepository<TransactionBdd, Integer> {

	List<TransactionBdd> findByAccountId(int accountId);

}
