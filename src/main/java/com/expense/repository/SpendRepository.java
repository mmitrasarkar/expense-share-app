package com.expense.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.expense.model.Spend;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the repository to connect with spend collection
 * 
 * @author USER
 *
 */
@Repository
public class SpendRepository {

	Logger logger = LoggerFactory.getLogger(SpendRepository.class);

	@Autowired
	MongoTemplate template;

	/**
	 * Insert into spend collection
	 * 
	 * @param spends
	 */
	public void insert(List<Spend> spends) {
		logger.info("Inserting spends");
		template.insert(spends, Spend.class);
		logger.info("Inserted spends");
	}

	/**
	 * Get all spends by a group
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Spend> getAllSpends(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		return template.find(query, Spend.class);

	}

	/**
	 * Delete the spends ...mostly required while purging a group information on
	 * deleting the group
	 * 
	 * @param groupId
	 */
	public void deleteSpends(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		template.findAllAndRemove(query, Spend.class);

	}

	/**
	 * For a particular group how much total spend that aggregation it will give
	 * @param groupId
	 * @return
	 */
	public double totalSpendByGroup(String groupId) {
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(Criteria.where("groupId").is(groupId)),
				Aggregation.group().sum("spendAmout").as("totalSpend"),
				Aggregation.project("totalSpend").andExclude("_id"));

		AggregationResults<Document> groupResults = template.aggregate(agg, "spend", Document.class);

		Document result = groupResults.getUniqueMappedResult();
		logger.info("Data: {} ", result);
		
		return result.getDouble("totalSpend");

	}

}
