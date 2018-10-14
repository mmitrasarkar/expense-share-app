package com.expense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense.model.PaymentHistory;
import com.expense.repository.PaymentHistoryRepository;

@RestController
public class PaymentHistoryController {
	
	@Autowired
	PaymentHistoryRepository pamentHistoryRepo;
	
	@GetMapping("/paymentHistories/{groupId}")
	public List<PaymentHistory> retrievePaymentDetails(@PathVariable String groupId,@RequestParam String payerId,@RequestParam String receiverId) {
		return pamentHistoryRepo.getPaymentDetails(payerId, receiverId, groupId);
	}

}
