package com.expense.repository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.expense.model.Group;
import com.expense.model.Settlement;
import com.mongodb.client.result.UpdateResult;

@Repository
public class SettlementRepository {

	Logger logger = LoggerFactory.getLogger(SettlementRepository.class);

	@Autowired
	MongoTemplate template;
	
	public void insert(List<Settlement> settlements) {
		logger.info("Inserting settlements {}",settlements);
		template.insert(settlements, Settlement.class);
		logger.info("Inserted settlements");
	}

	public List<Settlement> getSettlement(String payerId, String receiverId, String groupId) {
		Query query = new Query(
				Criteria.where("groupId").is(groupId).andOperator(Criteria.where("payerId").is(payerId),
						Criteria.where("receiverId").is(receiverId)

				));

		logger.info("Query: {} ", query);
		List<Settlement> data = template.find(query, Settlement.class);
		logger.info("Data: {} ", data);
		return data;
	}

	public void updateSettlement(Settlement settlement, String groupId) {
		Query query = new Query(
				Criteria.where("groupId").is(groupId).andOperator(Criteria.where("payerId").is(settlement.getPayerId()),
						Criteria.where("receiverId").is(settlement.getReceiverId())

				));
		
		Update update = new Update().set("amountToBePaid", (settlement.getAmountToBePaid()-settlement.getAmountPaid())).set("amountPaid", settlement.getAmountPaid()).set("updateDate", new Date());

		logger.info("Query: {} ", query);
		logger.info("Amount to be paid updated to : {} ",(settlement.getAmountToBePaid()-settlement.getAmountPaid()));
		UpdateResult data = template.updateFirst(query, update, Settlement.class);
		logger.info("Data: {} ", data.getModifiedCount());
	
	}
	
	public void delete(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		template.findAllAndRemove(query, Settlement.class);

	}
}
