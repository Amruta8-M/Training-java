package com.training.bean;

public class Transaction {
	private Integer account_id;
	private Integer credit;
	private Integer debit;
	
	public Transaction(){
		
	}
	
	public Integer getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public Integer getDebit() {
		return debit;
	}
	public void setDebit(Integer debit) {
		this.debit = debit;
	}
	@Override
	public String toString() {
		return "Transaction [account_id=" + account_id + ", credit=" + credit + ", debit=" + debit + "]";
	}
	public Transaction(Integer account_id, Integer credit, Integer debit) {
		super();
		this.account_id = account_id;
		this.credit = credit;
		this.debit = debit;
	}
	

}
