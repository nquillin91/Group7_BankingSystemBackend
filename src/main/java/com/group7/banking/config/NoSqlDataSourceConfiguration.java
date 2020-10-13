package com.group7.banking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

@Configuration
@EnableMongoRepositories(basePackages = { "com.group7.banking.repository.nosql" })
@EnableTransactionManagement
public class NoSqlDataSourceConfiguration extends AbstractMongoClientConfiguration {
	
	@Value("${app.datasource.nosql.uri}")
	private String mongoUri;
	
	@Override
	protected void configureClientSettings(MongoClientSettings.Builder builder) {
		builder.applyConnectionString(new ConnectionString(mongoUri));
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.group7.banking.model.nosql";
	}

	@Override
	protected String getDatabaseName() {
		return "banking_app";
	}
}