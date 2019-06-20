package com.gunes.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.BigDecimalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;


@Configuration
@PropertySource(value = {"classpath:/${env}/mongo.properties"})
public class MongoDBConfiguration {

    @Autowired
    private Environment env;

    private MongoClient mongoClient;

    @Bean
    public MongoClient mongoClient() {
        List<ServerAddress> serverAddresses = new ArrayList<>();
        serverAddresses.add(new ServerAddress(env.getProperty("mongodb.url"), Integer.parseInt(env.getProperty("mongodb.port"))));
        MongoClientOptions options = MongoClientOptions.builder()
                .readPreference(ReadPreference.primaryPreferred())
                .maxConnectionIdleTime(3000)
                .sslEnabled(false)
                .build();
        mongoClient = new MongoClient(serverAddresses, options);
        return mongoClient;
    }

    @Bean
    @DependsOn({"mongoClient"})
    public Datastore datastore() {
        final Morphia morphia = new Morphia();
        final Datastore datastore = morphia.createDatastore(mongoClient, env.getProperty("mongodb.database"));
        morphia.mapPackage("com.gunes.model.document");
        datastore.ensureIndexes();
        morphia.getMapper().getConverters().addConverter(BigDecimalConverter.class);
        return datastore;
    }
}