package fr.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

/**
 * Application to manage simple bank operations on an account.
 *
 */
@SpringBootApplication
@EncryptablePropertySource("classpath:application.yml")
public class ManageAccountApp {
	
    public static void main( String[] args ) {
    	SpringApplication.run(ManageAccountApp.class, args);
    }
    
}
