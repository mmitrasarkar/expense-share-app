package com.expense.model;

import org.springframework.data.annotation.Id;

/**
 * Member Entity class
 * @author USER
 *
 */
public class Member implements Comparable<Member>{
	@Id
	private String memberId;
	private String memberName;
	private String email;
	private int balance;
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int compareTo(Member o) {
		if( this.balance>o.balance)
			return 1;
		else if (this.balance>o.balance)
			return -1;
		return 0;
	
	}
	

}
