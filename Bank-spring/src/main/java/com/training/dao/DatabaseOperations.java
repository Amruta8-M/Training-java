package com.training.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.training.bean.Transaction;
import com.training.bean.User;

@Repository
public class DatabaseOperations {
	
	@Autowired
	UserMapper mapper;
	
	@Autowired
	TransactionMapper tMapper;
	
	String qry;
	String qry1;
	
	DataSource datasource;
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate =new JdbcTemplate(dataSource);
	}
	
	//Get the count of the users
	 public int getCountofUsers() {
		//qry="select count(*) from accounts";
		return jdbcTemplate.queryForObject("select count(*) from accounts", Integer.class);
	}

	public int createNewAccount(String userName, String password, String userLocation) {
		qry = "insert into accounts(user_name, wallet_amount, password,userLocation) values(?, 00, ?,?)";
		 return jdbcTemplate.update(qry, userName,password,userLocation);	
	}

	public int login(int loginId, String password) {
		qry="select account_id from accounts where account_id = ? and password= ?";
		return jdbcTemplate.queryForObject(qry, new Object[] {loginId, password}, Integer.class);	
	}

	public int checkBalance(int accountId) {
		qry = "select wallet_amount from accounts where account_id = ?";
		return jdbcTemplate.queryForObject(qry, new Object[] {accountId}, Integer.class);
	}

	public int addAmount(int accountId, int amount) {
		qry = "update accounts set wallet_amount = wallet_amount + ? where account_id = ?";
		 jdbcTemplate.update(qry,amount,accountId);
		 
		 qry1="insert into transactions(account_id, credit) values(?, ?)";
		return jdbcTemplate.update(qry1, accountId,amount);	
	}

	public int withdrawAmount(int accountId, int amount) {
		qry = "update accounts set wallet_amount = wallet_amount - ? where account_id = ? and wallet_amount>= ?";
		qry1="insert into transactions(account_id, debit) values(?, ?)";
		if(jdbcTemplate.update(qry,amount,accountId,amount)>0) {
			return jdbcTemplate.update(qry1,accountId,amount);
		}
		return 0;	
	}

	public int transferAmount(int accountId, int accountId1, int amount) {
		qry = "update accounts set wallet_amount = wallet_amount - ? where account_id = ? and wallet_amount>= ?";
		qry1 = "update accounts set wallet_amount = wallet_amount + ? where account_id = ?";
		String qry2 ="insert into transactions(account_id, debit) values(?, ?)";
		String qry3 ="insert into transactions(account_id, credit) values(?, ?)";
		if(jdbcTemplate.update(qry,amount,accountId,amount)>0){
			 jdbcTemplate.update(qry1,amount,accountId1);
			 jdbcTemplate.update(qry2,accountId,amount);
			 return jdbcTemplate.update(qry3,accountId1,amount);
		}
		return 0;
		
	}

	public Transaction transactionHistory(int accountId) {
		qry="select * from transactions where account_id = ?";
		return jdbcTemplate.queryForObject(qry, tMapper, accountId);
		
	}

	public User getUserDetailsById(int id) {
		qry = "select * from accounts where account_id = ?";
		
		return jdbcTemplate.queryForObject(qry, new Object[] {id}, mapper);
	}

	public boolean deleteUserAccount(int id) {
		qry="delete from accounts where account_id= ?";
		int a=jdbcTemplate.update(qry, new Object[] {id});
		if(a>0)
			return true;
		return false;
	}

}

@Component
class UserMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User theUser=new User();
//		Map the fields of User to the columns of the table:userdetails
		theUser.setAccount_id(rs.getInt("account_id"));
		theUser.setUser_name(rs.getString("user_name"));
		theUser.setUserLocation(rs.getString("userLocation"));
		theUser.setPassword(rs.getString("password"));
		theUser.setWallet_amount(rs.getInt("wallet_amount"));
		
		return theUser;
	}
}
@Component
class TransactionMapper implements RowMapper<Transaction>{

	@Override
	public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transaction theTransaction= new Transaction();
		theTransaction.setAccount_id(rs.getInt("account_id"));
		theTransaction.setCredit(rs.getInt("credit"));
		theTransaction.setDebit(rs.getInt("debit"));
		
		return theTransaction;
	}
	
}
