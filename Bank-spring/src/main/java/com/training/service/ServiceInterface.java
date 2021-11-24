package com.training.service;

import org.springframework.stereotype.Service;


public interface ServiceInterface {
	
	//boolean connectTODb();

	void createNewAccount(String userName, String password, int amount);

	int login();

	void checkBalance(int accountId);

	void addAmount(int accountId, int nextInt);
	
	void withdrawAmount(int accountId, int nextInt);

	void transferAmount(int accountId, int nextInt, int nextInt2);

	void transactionHistory(int accountId);

	
	
	

}
