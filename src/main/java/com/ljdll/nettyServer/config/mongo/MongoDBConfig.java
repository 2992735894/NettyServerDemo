//package com.ljdll.nettyServer.config.mongo;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.MongoTransactionManager;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 将mongodb的事务管理器注入到spring容器中，目前好像会影响mysql、达梦等数据库的事务管理
// */
//@Configuration
//public class MongoDBConfig {
//
//    @Bean(name = "mysqlTransactionManager")
//    @Primary
//    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "mongoTransactionManager")
//    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory databaseFactory) {
//        return new MongoTransactionManager(databaseFactory);
//    }
//}
