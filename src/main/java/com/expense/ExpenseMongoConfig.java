package com.expense;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class ExpenseMongoConfig {
	
	public @Bean MongoClient mongoClient() throws Exception
	{
		return new MongoClient("localhost");
	}
	
	 public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		    return new SimpleMongoDbFactory(mongoClient(), "expensedb");
		  }
	 public @Bean MongoTemplate mongoTemplate() throws Exception {
		    return new MongoTemplate(mongoDbFactory());
		  }

}
