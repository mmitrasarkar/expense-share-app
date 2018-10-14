package com.expense.model;

public class SpendSummary {
	
	private String groupId;
	private int totalCost;
	private int perPsersonCost;
	private int pendingSettlement;
	public String getGroupId() {
		return groupId;
	}
	public int getTotalCost() {
		return totalCost;
	}
	public int getPerPsersonCost() {
		return perPsersonCost;
	}
	public int getPendingSettlement() {
		return pendingSettlement;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	public void setPerPsersonCost(int perPsersonCost) {
		this.perPsersonCost = perPsersonCost;
	}
	public void setPendingSettlement(int pendingSettlement) {
		this.pendingSettlement = pendingSettlement;
	}
	

}
