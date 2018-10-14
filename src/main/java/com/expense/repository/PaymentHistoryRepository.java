package com.expense.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.expense.controller.GroupController;
import com.expense.model.PaymentHistory;

@Repository
public class PaymentHistoryRepository {
	
	Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	MongoTemplate template;
	
	public void insert(PaymentHistory paymentHistory,String groupId)
	{
		logger.info("Inserting Payment History {}",paymentHistory);
		template.insert(paymentHistory);
		logger.info("Inserted Payment");
	}
	
	public void delete(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		template.findAllAndRemove(query, PaymentHistory.class);

	}
	
	public List<PaymentHistory> getPaymentDetails(String payerId, String receiverId, String groupId) {
		Query query = new Query(
				Criteria.where("groupId").is(groupId).andOperator(Criteria.where("payerId").is(payerId),
						Criteria.where("receiverId").is(receiverId)

				));

		logger.info("Query: {} ", query);
		List<PaymentHistory> data = template.find(query, PaymentHistory.class);
		logger.info("Data: {} ", data);
		return data;
	}

}
