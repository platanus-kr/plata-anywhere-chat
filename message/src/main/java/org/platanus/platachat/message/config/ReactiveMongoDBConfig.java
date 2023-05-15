package org.platanus.platachat.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@EnableReactiveMongoRepositories
public class ReactiveMongoDBConfig extends AbstractReactiveMongoConfiguration {
	
	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create();
	}
	
	@Override
	protected String getDatabaseName() {
		return "pac";
	}
}
