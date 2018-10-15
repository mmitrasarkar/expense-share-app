package com.expense.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
/**
 * Payment history entity class
 * @author USER
 *
 */
public class PaymentHistory {


	@Id
	private String id;
	private String groupId;
	private String receiverId;
	private String payerId;
	private Double amountPaid;
	private Date paymentDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	
}
