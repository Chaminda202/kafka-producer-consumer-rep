package com.spring.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.spring.kafka.model.Bank;

@Service
public class ConsumerService {
	private Logger logger;

	public ConsumerService() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	/*@KafkaListener(topics = "${kafka.sample.topic}")
	public void secondTopicListener(Bank payload) {
		logger.info("received json payload = {}", JacksonUtil.getToString(payload));
	}*/

	@KafkaListener(topics = "${kafka.sample.topic}")
	public void receive(@Payload Bank data, @Headers MessageHeaders headers) {
		logger.info("received payload ='{}'", data);

		headers.keySet().forEach(key -> {
			logger.info("{}: {}", key, headers.get(key));
		});
	}
}
