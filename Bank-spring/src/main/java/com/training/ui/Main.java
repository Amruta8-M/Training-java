package com.training.ui;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.training.bean.*;
import com.training.dao.DatabaseOperations;
import com.training.service.ServiceClass;
//import com.training.service.ServiceInterface;



public class Main {
	static int accountId;

	public static void main(String[] args) {
		Scanner scan= new Scanner(System.in);
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("Config.xml");
		ServiceClass theService= new ServiceClass();
		
		
		//Get reference to bean
		//using service layer
		//ServiceClass theService = context.getBean("serviceClass", ServiceClass.class);
    	
		//using dao layer
		DatabaseOperations theOperate = context.getBean("databaseOperations", DatabaseOperations.class);
    	//System.out.println("total number of registerd user are "+theOperate.getCountofUsers());
		
//		int userId=9;
//		User theUser= theOperate.getUserDetailsById(userId);
//		System.out.println("Name for the given id : " + theUser.getUser_name());
//    	System.out.println("Salary for the given id : " + theUser.getWallet_amount());
//    	
//    	System.out.println(theOperate.deleteUserAccount(userId)); 
		
		while(true) {
    		System.out.println();
    		System.out.println();
    	System.out.println("enter your choice:");
    	System.out.println(" Enter 1 to create new account");
    	System.out.println("enter 2 to login to your account");
    	int choice1=scan.nextInt();
    	
    	if(choice1==1) {
    		System.out.println("enter new user details: ");
    		System.out.println("enter  username");
    		String userName=scan.next();
    		System.out.println("enter your password");
    		String password=scan.next();
    		System.out.println("enter user location");
    		String userLocation=scan.next();
    		System.out.println( theOperate.createNewAccount(userName,password,userLocation));
    	}
    	else if(choice1==2) {
    		System.out.println("enter your login details");
        	System.out.println("your account ID:");
        	int loginId=scan.nextInt();
        	System.out.println("password");
        	String password=scan.next();
    		 accountId=theOperate.login(loginId,password);
    		 System.out.println(accountId);
    		 
    		 // operations after loggin
    		 while(accountId!=0) {
    	    		System.out.println("");
    	    		System.out.println("");
    	    	System.out.println("choose the action");
    	    	
    	    	System.out.println("Enter 1 for checking balance");
    	    	System.out.println("Enter 2 for transaction");
    	    	System.out.println("Enter 3 for transaction history");
    	    	System.out.println("Enter 4 for Logout");

    	    	
    	    	int choice=scan.nextInt();
    	    	
    	    	// check balance
    	    	 if(choice==1) {
    	    		//System.out.println("to check balance enter your account id");
    	    		System.out.println( "your balance = " + theOperate.checkBalance(accountId));
    	    	}
    	    	
    	    	//transaction 
    	    	else if(choice==2) {
    	    		System.out.println("choose the action");
    	        	System.out.println("Enter 1 for adding amount");
    	        	System.out.println("Enter 2 for withdrawal");
    	        	System.out.println("Enter 3 for transfering amount");
    	        	
    	        	int choice2=scan.nextInt();
    	        	
    	        	//adding amount
    	        	if(choice2==1) {
    	        		System.out.println("enter the amount to add");
    	        		int add=theOperate.addAmount(accountId, scan.nextInt());
    	        		System.out.println("amount added successfully to your account"+add);
    	        	}
    	        	
    	        	//withdraval
    	        	else if(choice2==2) {
    	        		System.out.println("enter the amount to withdraw");

    	        		if(theOperate.withdrawAmount(accountId, scan.nextInt())>0)
    	        		System.out.println("amount withdrawn successfully");
    	        		else
    	        			System.out.println("You don't have sufficient amount...");
    	        	}
    	        	
    	        	//transfer amount
    	        	else if(choice2==3) {
    	        		System.out.println("enter new account number and amount to transfer");
    	        		
    	        		if(theOperate.transferAmount(accountId, scan.nextInt(), scan.nextInt())>0)
    	        		System.out.println("amount transfered successfully..");
    	        		else
    	        			System.out.println("You don't have sufficient amount to transfer");
    	        	}
    	        	else {
    	        		System.out.println("not valid choice");
    	        	}
    	    	}
    	    	
    	    	//transaction history
    	    	else if(choice==3) {
    	    		
    	    		Transaction theTransaction=theOperate.transactionHistory(accountId);
    	    		System.out.println("The Account number : " + theTransaction.getAccount_id()+" credited with RS"+theTransaction.getCredit());
    	    	}
    	    	 
    	    	 //Logout
    	    	else if(choice==4) {
    	    		accountId=0;
    	    		System.out.println("Logged out from your account...");
    	    	}
    	    	else {
    	    		System.out.println("not valid choice");
    	    	}
    	    	}
    		 
    	}
    	}
	}

}
