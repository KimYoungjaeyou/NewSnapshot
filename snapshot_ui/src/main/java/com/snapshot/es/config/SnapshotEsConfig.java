package com.snapshot.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnapshotEsConfig {
	
    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;
    
    
    @Bean 
    public RestClient getRestClient(){        
        return  RestClient.builder(
        		new HttpHost(host, port, "http")).build();
    }
}
