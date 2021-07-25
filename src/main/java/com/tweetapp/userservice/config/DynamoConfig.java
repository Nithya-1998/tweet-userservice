package com.tweetapp.userservice.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * @author Nithya T AWS dynamo connect config
 */
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.tweetapp.userservice.repository")
public class DynamoConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String endpoint;
	@Value("${amazon.aws.accesskey}")
	private String accesskey;
	@Value("${amazon.aws.secretkey}")
	private String secretkey;
	@Value("${amazon.aws.region}")
	private String region;

//	@Bean
//	public DynamoDBMapper mapper() {
//		System.out.println("Inside Dynamodb config");
//		return new DynamoDBMapper(amazonDynamoDB());
//	}

	@Bean
	public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
		return new AwsClientBuilder.EndpointConfiguration(endpoint, region);
	}

	@Bean
	public AWSCredentialsProvider awsCredentialsProvider() {
		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesskey, secretkey));
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfiguration())
				.withCredentials(awsCredentialsProvider()).build();
	}

}