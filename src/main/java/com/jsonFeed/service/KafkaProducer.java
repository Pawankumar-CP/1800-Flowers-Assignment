package com.jsonFeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class KafkaProducer {
	
	
    private static final String TOPIC = "json";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    
    public void sendMessage(String finalUserData) throws JsonProcessingException {
        this.kafkaTemplate.send(TOPIC, finalUserData);
    }
	
	
}
