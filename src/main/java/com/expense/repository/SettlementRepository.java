package com.expense.repository;

import java.util.Date;
import java.util.List;

import org.bson.Document;
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

import com.expense.model.Settlement;
import com.mongodb.client.result.UpdateResult;

@Repository
public class SettlementRepository {

	Logger logger = LoggerFactory.getLogger(SettlementRepository.class);

	@Autowired
	MongoTemplate template;
	/**
	 * Insert settlement
	 * @param settlements
	 */
	public void insert(List<Settlement> settlements) {
		logger.info("Inserting settlements");
		template.insert(settlements, Settlement.class);
		logger.info("Inserted settlements");
	}
	/**
	 * Get settlement by group
	 * @param groupId
	 * @return
	 */
	public List<Settlement> getSettlementsByGroup(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));

		logger.info("Query: {} ", query);
		List<Settlement> data = template.find(query, Settlement.class);
		logger.info("Data: {} ", data);
		return data;
	}
	/**
	 * 
	 * @param payerId
	 * @param receiverId
	 * @param groupId
	 * @return
	 */
	public List<Settlement> getSettlement(String payerId, String receiverId, String groupId) {
		Query query = null;
		if (payerId == null || payerId.isEmpty())
			query = new Query(
					Criteria.where("groupId").is(groupId).andOperator(Criteria.where("receiverId").is(receiverId)

					));

		else if (receiverId == null || receiverId.isEmpty())
			query = new Query(Criteria.where("groupId").is(groupId).andOperator(Criteria.where("payerId").is(payerId)

			));
		else if ((receiverId == null || receiverId.isEmpty()) && (payerId == null || payerId.isEmpty()))
			query = new Query(Criteria.where("groupId").is(groupId));
		else
			query = new Query(Criteria.where("groupId").is(groupId).andOperator(Criteria.where("payerId").is(payerId),
					Criteria.where("receiverId").is(receiverId)

			));
		logger.info("Query: {} ", query);
		List<Settlement> data = template.find(query, Settlement.class);
		logger.info("Data: {} ", data);
		return data;
	}
	/**
	 * Update Settlement
	 * @param settlement
	 * @param groupId
	 */
	public void updateSettlement(Settlement settlement, String groupId) {
		Query query = new Query(
				Criteria.where("groupId").is(groupId).andOperator(Criteria.where("payerId").is(settlement.getPayerId()),
						Criteria.where("receiverId").is(settlement.getReceiverId())

				));

		Update update = new Update()
				.set("amountToBePaid", (settlement.getAmountToBePaid() - settlement.getAmountPaid()))
				.set("amountPaid", settlement.getAmountPaid()).set("updateDate", new Date());

		logger.info("Query: {} ", query);
		logger.info("Amount to be paid updated to : {} ",
				(settlement.getAmountToBePaid() - settlement.getAmountPaid()));
		UpdateResult data = template.updateFirst(query, update, Settlement.class);
		
		logger.info("Data: {} ", data.getModifiedCount());

	}
	/**
	 * Delete settlement for the group when the group is purged
	 * @param groupId
	 */
	public void delete(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		template.findAllAndRemove(query, Settlement.class);

	}
	
	/**
	 * For a particular group how much total settlement pending
	 * @param groupId
	 * @return
	 */
	public double totalPendingSettlementByGroup(String groupId) {
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(Criteria.where("groupId").is(groupId)),
				Aggregation.group().sum("amountToBePaid").as("settlementPending"),
				Aggregation.project("settlementPending").andExclude("_id"));

		AggregationResults<Document> groupResults = template.aggregate(agg, "settlement", Document.class);

		logger.info("Data: {} ", groupResults.getUniqueMappedResult());
		return groupResults.getUniqueMappedResult().getDouble("settlementPending");

	}

}
