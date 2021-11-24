package com.training.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

@Repository("dao")
public class Dao implements DaoInterface{
	public static final String URLToConnect="jdbc:mysql://localhost:3306/bankXYZ";
	public static final String USERName="root";
	public static final String USERPassword="root";
	String qry;
	String qry1;
	Connection dbCon;
	Statement stmt;	
	PreparedStatement thePreparedStatement;
	 static int accountId=0;
	Scanner scan=new Scanner(System.in);
	
	public boolean connectToDb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			dbCon=DriverManager.getConnection(URLToConnect, USERName, USERPassword);
			stmt = dbCon.createStatement();
			System.out.println("connected to database...");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found.."+ e.getMessage());
		} catch (SQLException e) {
			System.out.println("can't connect to database.."+e.getMessage());
		}
		return true;
	}
	
	public int login() {
		try {
		System.out.println("enter your login details");
    	System.out.println("your account ID:");
    	int loginId=scan.nextInt();
    	System.out.println("password");
    	String password=scan.next();
		
    	
    	qry="select * from accounts where account_id = ? and password= ?";
    	try {
			thePreparedStatement= dbCon.prepareStatement(qry);
			thePreparedStatement.setInt(1, loginId);
			thePreparedStatement.setString(2, password);
			
			ResultSet theResultSet= thePreparedStatement.executeQuery();
			if(theResultSet.next()) {
				accountId=loginId;
					
			}
			else {
				System.out.println("invalid login details");
			}
			
		} catch (SQLException e) {
			System.out.println("can't execute the query.."+e.getMessage());
		}
    	}catch (InputMismatchException e) {
    		System.out.println("please enter valid inputs.."+e.getMessage());
    	}
    	return accountId;	
	}

	public void createNewAccount(String USERName, String password, int balance) {
		qry = "insert into accounts(user_name, wallet_amount, password) values(?, ?, ?)";
		 qry1="insert into transactions(account_id, credit) values(?, ?)";
		  //String qry2="select account_id from accounts where user_name= ?";
		
    	try {
//        	Get the PreparedStatement object
			thePreparedStatement = dbCon.prepareStatement(qry);

//			Put the values for ?
			thePreparedStatement.setString(1, USERName);
			thePreparedStatement.setString(2, password);
			thePreparedStatement.setInt(3, balance);
			
			
//			Execute the query
			if(thePreparedStatement.executeUpdate() > 0)
				System.out.println("User added successfully...");
			
			//ResultSet theResultSet= thePreparedStatement.executeQuery();
			
			
//	//		 Updating transaction 
//			 thePreparedStatement = dbCon.prepareStatement(qry1);
//				
////				Map the values to ?
//				thePreparedStatement.setInt(1, accountId);
//				thePreparedStatement.setInt(2, balance);
//				
//				if(thePreparedStatement.executeUpdate() > 0)
//					System.out.println("transaction updated");
		} catch (SQLException e) {
			System.out.println("Issues with PreparedStatement insert query : " + e.getMessage());
		}
		
	}
	
	public void checkBalance(int accountID) {
		

		//System.out.println(accountID);
		qry = "select wallet_amount from accounts where account_id = ?";
		
		try {
				thePreparedStatement = dbCon.prepareStatement(qry);
			
//			Map the values to ?
			thePreparedStatement.setInt(1, accountID);
				
			
			
			ResultSet theResultSet= thePreparedStatement.executeQuery();
			System.out.println("your account balance is");
			
			// traversing through resultset
			while(theResultSet.next()) {
				System.out.println(theResultSet.getInt("wallet_amount"));
			}
		} catch (SQLException e) {
			System.out.println("can't execute the query.."+e.getMessage());
		}
		
	}
	
	public void addAmount(int accountId, int amount) {
		//System.out.println(accountId);
		qry = "update accounts set wallet_amount = wallet_amount + ? where account_id = ?";
		 qry1="insert into transactions(account_id, credit) values(?, ?)";
		
		
		try {
			
				thePreparedStatement = dbCon.prepareStatement(qry);
			
//			Map the values to ?
			thePreparedStatement.setInt(1, amount);
			thePreparedStatement.setInt(2, accountId);
			
			if(thePreparedStatement.executeUpdate() > 0) {
				System.out.println("amount added successfully to your account, Thank you...");
			 
//			 Updating transaction 
			 thePreparedStatement = dbCon.prepareStatement(qry1);
				
//				Map the values to ?
				thePreparedStatement.setInt(1, accountId);
				thePreparedStatement.setInt(2, amount);
				
				if(thePreparedStatement.executeUpdate() > 0)
					System.out.println("transaction updated");
			}
			
		}catch (SQLException e) {
			System.out.println("can't execute the query.."+e.getMessage());
		}
				
		
		
	}
	
	public void withdrawAmount(int accountId, int amount) {
qry = "update accounts set wallet_amount = wallet_amount - ? where account_id = ? and wallet_amount>= ?";
qry1="insert into transactions(account_id, debit) values(?, ?)";
		
		try {
				thePreparedStatement = dbCon.prepareStatement(qry);
			
//			Map the values to ?
			thePreparedStatement.setInt(1, amount);
			thePreparedStatement.setInt(2, accountId);
			thePreparedStatement.setInt(3, amount);
			
			if(thePreparedStatement.executeUpdate() > 0) {
				System.out.println("amount has been withdrawn successfully from your account, Thank you...");
			
//			 Updating transaction 
			 thePreparedStatement = dbCon.prepareStatement(qry1);
				
//				Map the values to ?
				thePreparedStatement.setInt(1, accountId);
				thePreparedStatement.setInt(2, amount);
				
				if(thePreparedStatement.executeUpdate() > 0)
					System.out.println("transaction updated");
			}
			
			else{
				System.out.println("insufficient balance");
			}
		}catch (SQLException e) {
			System.out.println("can't execute the query.."+e.getMessage());
		}
				
	
	
	}

	public void transferAmount(int accountId1, int accountId2, int amount ) {
qry = "update accounts set wallet_amount = wallet_amount - ? where account_id = ? and wallet_amount>= ?";
qry1 = "update accounts set wallet_amount = wallet_amount + ? where account_id = ?";
String qry2="insert into transactions(account_id, debit) values(?, ?)";
String qry3="insert into transactions(account_id, credit) values(?, ?)";

		
		try {
				thePreparedStatement = dbCon.prepareStatement(qry);
			
//			Map the values to ?
			thePreparedStatement.setInt(1, amount);
			thePreparedStatement.setInt(2, accountId1);
			thePreparedStatement.setInt(3, amount);
			
			if(thePreparedStatement.executeUpdate() > 0) {
				System.out.println("Your transaction is successfull");
			
//			Updating transaction 
			 thePreparedStatement = dbCon.prepareStatement(qry2);
				
//				Map the values to ?
				thePreparedStatement.setInt(1, accountId1);
				thePreparedStatement.setInt(2, amount);
				
				if(thePreparedStatement.executeUpdate() > 0) {
					System.out.println("transaction updated");
				}
					
					thePreparedStatement = dbCon.prepareStatement(qry1);
					
//					Map the values to ?
					thePreparedStatement.setInt(1, amount);
					thePreparedStatement.setInt(2, accountId2);
					
					if(thePreparedStatement.executeUpdate() > 0) {
						System.out.println("Thank you...");
					
//					Updating transaction 
					 thePreparedStatement = dbCon.prepareStatement(qry3);
						
//						Map the values to ?
						thePreparedStatement.setInt(1, accountId2);
						thePreparedStatement.setInt(2, amount);
						
						if(thePreparedStatement.executeUpdate() > 0)
							System.out.println("transaction updated");
			}
				}
			
			else{
				System.out.println("insufficient balance");
			}
			
			
			
		
		}catch (SQLException e) {
			System.out.println("can't execute the query.."+e.getMessage());
		}
		
		
	}

	public void transactionHistory(int accountId) {
		qry="select * from transactions where account_id = ?";
		
		try {
			thePreparedStatement = dbCon.prepareStatement(qry);
		
//		Map the values to ?
		thePreparedStatement.setInt(1, accountId);
			
		ResultSet theResultSet= thePreparedStatement.executeQuery();
		System.out.println("your account balance is");
		
		// traversing through resultset
		while(theResultSet.next()) {
			if(theResultSet.getInt("credit")!=0) {
			System.out.println("the amount credited = "+theResultSet.getInt("credit"));}
			else {
			System.out.println("the amount debited = "+theResultSet.getInt("debit"));
			}

		}
	} catch (SQLException e) {
		System.out.println("can't execute the query.."+e.getMessage());
	}
	
}
}
