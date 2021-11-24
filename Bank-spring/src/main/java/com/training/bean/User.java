package com.training.bean;

public class User {
private Integer account_id;
	
	private String user_name;
	private String userLocation;
	private String password;
	private Integer wallet_amount;
	
	
	public User() {
		
	}

	public Integer getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getWallet_amount() {
		return wallet_amount;
	}

	public void setWallet_amount(Integer wallet_amount) {
		this.wallet_amount = wallet_amount;
	}

	@Override
	public String toString() {
		return "User [account_id=" + account_id + ", user_name=" + user_name + ", userLocation=" + userLocation
				+ ", password=" + password + ", wallet_amount=" + wallet_amount + "]";
	}

	public User(Integer account_id, String user_name, String userLocation, String password, Integer wallet_amount) {
		super();
		this.account_id = account_id;
		this.user_name = user_name;
		this.userLocation = userLocation;
		this.password = password;
		this.wallet_amount = wallet_amount;
	}
	
	
}
