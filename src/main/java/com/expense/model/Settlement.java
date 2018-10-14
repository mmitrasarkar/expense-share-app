package com.expense.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Settlement {

	@Id
	private String settlementId;
	private String groupId;
	private String receiverId;
	private String payerId;
	private int amountToBePaid;
	private int amountPaid;
	private Date createDate;
	private Date updateDate;
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	private String type;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAmountToBePaid() {
		return amountToBePaid;
	}
	public void setAmountToBePaid(int amountToBePaid) {
		this.amountToBePaid = amountToBePaid;
	}
	public int getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(int amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payeeId) {
		this.payerId = payeeId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
