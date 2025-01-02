package com.myproject.myspringproject.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TranctionConfig {

    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory  dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
}
