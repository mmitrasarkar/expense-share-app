package com.expense.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.model.PaymentHistory;
import com.expense.model.Settlement;
import com.expense.repository.PaymentHistoryRepository;
import com.expense.repository.SettlementRepository;

@RestController
public class SettlementController {

	Logger logger = LoggerFactory.getLogger(SettlementController.class);
	@Autowired
	private SettlementRepository settlementRepo;
	
	@Autowired
	PaymentHistoryRepository paymentHistoryRepo;
	

	@GetMapping("/settlements/{groupId}")
	public List<Settlement> retrieveAllSettlements(@PathVariable String groupId,@RequestParam(required=false) String payerId,@RequestParam(required=false) String receiverId) {
		return settlementRepo.getSettlement(payerId, receiverId, groupId);
	}
	
	@PutMapping("/settlements/{groupId}")
	public void updateSettlement(@PathVariable String groupId,@RequestBody Settlement settlement) {
		PaymentHistory paymentHistory = new PaymentHistory();
		paymentHistory.setPayerId(settlement.getPayerId());
		paymentHistory.setReceiverId(settlement.getReceiverId());
		paymentHistory.setPaymentDate(new Date());
		paymentHistory.setAmountPaid(settlement.getAmountPaid());
		paymentHistory.setGroupId(groupId);
		/**
		 * TO-DO multiple collection transaction 
		 */
		settlementRepo.updateSettlement(settlement, groupId);
		paymentHistoryRepo.insert(paymentHistory, groupId);
	}
	
	@DeleteMapping("/settlements/{groupId}")
	public void deleteSpends(@PathVariable String groupId) {
		logger.info("Inside Spend delete service {}", groupId);
		settlementRepo.delete(groupId);
	}

	

}
