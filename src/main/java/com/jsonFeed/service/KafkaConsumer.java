package com.jsonFeed.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    @KafkaListener(topics = "json", groupId = "group_id")
    public void consume(String message) throws IOException {
    }
	
	
}
