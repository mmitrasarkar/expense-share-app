package com.expense.model;

import org.springframework.data.annotation.Id;

public class Spend {

	@Id
	private String spendId;
	private String groupId;
	private int spendAmout;
	private String description;
	private String spenderName;
	public int getSpendAmout() {
		return spendAmout;
	}
	public void setSpendAmout(int spendAmout) {
		this.spendAmout = spendAmout;
	}
	public String getSpenderName() {
		return spenderName;
	}
	public void setSpenderName(String spenderName) {
		this.spenderName = spenderName;
	}
	private Integer numberOfMembers;
	public Integer getNumberOfMembers() {
		return numberOfMembers;
	}
	public void setNumberOfMembers(Integer numberOfMembers) {
		this.numberOfMembers = numberOfMembers;
	}
	public String getSpendId() {
		return spendId;
	}
	public void setSpendId(String spendId) {
		this.spendId = spendId;
	}
	public int getSendAmout() {
		return spendAmout;
	}
	public void setSendAmout(int sendAmout) {
		this.spendAmout = sendAmout;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
