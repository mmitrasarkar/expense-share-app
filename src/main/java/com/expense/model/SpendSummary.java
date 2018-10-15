package com.expense.model;
/**
 * Spend Summary
 * @author USER
 *
 */
public class SpendSummary {
	
	private String groupId;
	private double totalCost;
	private double perPsersonCost;
	private double pendingSettlement;
	public String getGroupId() {
		return groupId;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public double getPerPsersonCost() {
		return perPsersonCost;
	}
	public double getPendingSettlement() {
		return pendingSettlement;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public void setPerPsersonCost(double perPsersonCost) {
		this.perPsersonCost = perPsersonCost;
	}
	public void setPendingSettlement(double pendingSettlement) {
		this.pendingSettlement = pendingSettlement;
	}
	

}
