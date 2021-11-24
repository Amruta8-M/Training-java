package com.training.dao;

public interface DaoInterface {
	boolean connectToDb();
	int login();
	void createNewAccount(String USERName, String password, int balance);
	void checkBalance(int accountID);
	//void addAmount(int accountId, int amount);
	void withdrawAmount(int accountId, int amount);
	void transferAmount(int accountId1, int accountId2, int amount );
	void transactionHistory(int accountId);
	

}
