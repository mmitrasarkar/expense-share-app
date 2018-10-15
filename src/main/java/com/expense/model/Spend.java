package com.expense.model;

import org.springframework.data.annotation.Id;

/**
 * Spend entity
 * 
 * @author USER
 *
 */
public class Spend {

	@Id
	private String spendId;
	private String groupId;
	private double spendAmout;
	private String description;
	private String spenderName;

	public double getSpendAmout() {
		return spendAmout;
	}

	public void setSpendAmout(double spendAmout) {
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
