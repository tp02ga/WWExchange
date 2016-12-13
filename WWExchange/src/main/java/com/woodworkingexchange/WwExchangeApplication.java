package com.woodworkingexchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

@SpringBootApplication
public class WwExchangeApplication extends SpringBootServletInitializer 
{
	public static void main(String[] args) {
		SpringApplication.run(WwExchangeApplication.class, args);
	}
	
	@Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;
  
  @Value("${cloud.aws.region}")
  private String region;

  
  @Bean
  public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
    AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
    amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
    return amazonS3Client;
  }
  
  @Bean
  public BasicAWSCredentials basicAWSCredentials() {
    return new BasicAWSCredentials(accessKey, secretKey);
  }
}
