package com.spring.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	private Logger logger;
	
	public ConsumerService() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@KafkaListener(topics = "${kafka.sample.topic}")
	public void secondTopicListener(String payload) {
		logger.info("received payload='{}'", payload);
	}
}
