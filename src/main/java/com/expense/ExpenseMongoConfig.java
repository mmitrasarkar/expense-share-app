package com.expense;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;

@Configuration
@Component
public class ExpenseMongoConfig {
	@Value("${spring.data.mongodb.host}")
	private String mongoDbHost;

	@Value("${spring.data.mongodb.database}")
	private String mongoDatabase;

	public @Bean MongoClient mongoClient() throws Exception {
		return new MongoClient("localhost");
	}

	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(mongoClient(), mongoDatabase);
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

}
