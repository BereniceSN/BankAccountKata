/**
 * 
 */
package fr.example.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.example.bank.entities.AccountBdd;

/**
 * @author beren
 *
 */
public interface AccountRepository extends JpaRepository<AccountBdd, Integer> {

}
