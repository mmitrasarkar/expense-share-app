package com.expense.repository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.expense.controller.GroupController;
import com.expense.model.PaymentHistory;
import com.expense.model.Settlement;
import com.mongodb.client.result.UpdateResult;

@Repository
public class PaymentHistoryRepository {

	Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	MongoTemplate template;

	public void insert(PaymentHistory paymentHistory, String groupId) {
		logger.info("Inserting Payment History {}", paymentHistory);
		template.insert(paymentHistory);
		logger.info("Inserted Payment");
	}

	public void delete(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		template.findAllAndRemove(query, PaymentHistory.class);

	}

	public List<PaymentHistory> getPaymentDetails(String payerId, String receiverId, String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId).andOperator(Criteria.where("payerId").is(payerId),
				Criteria.where("receiverId").is(receiverId)

		));

		logger.info("Query: {} ", query);
		List<PaymentHistory> data = template.find(query, PaymentHistory.class);
		logger.info("Data: {} ", data);
		return data;
	}

	public Integer getTotalPaymentByUser(String payerId, String receiverId, String groupId) {

		Aggregation agg = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("groupId").is(groupId).andOperator(
						Criteria.where("payerId").is(payerId), Criteria.where("receiverId").is(receiverId))),
				Aggregation.group().sum("amountPaid").as("totalpayment"),
				Aggregation.project("totalpayment").andExclude("_id"));

		AggregationResults<Integer> groupResults = template.aggregate(agg, "feature", Integer.class);

		logger.info("Data: {} ", groupResults.getUniqueMappedResult());
		return groupResults.getUniqueMappedResult();

	}

}
