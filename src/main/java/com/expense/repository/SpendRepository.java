package com.expense.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.expense.model.Spend;

@Repository
public class SpendRepository {

	Logger logger = LoggerFactory.getLogger(SpendRepository.class);

	@Autowired
	MongoTemplate template;

	public void insert(List<Spend> spends) {
		logger.info("Inserting spends");
		template.insert(spends, Spend.class);
		logger.info("Inserted spends");
	}

	public List<Spend> getAllSpends(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		return template.find(query, Spend.class);

	}
	
	public void deleteSpends(String groupId) {
		Query query = new Query(Criteria.where("groupId").is(groupId));
		template.findAllAndRemove(query, Spend.class);

	}

}
