package com.spring.kafka.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.spring.kafka.model.Bank;

@Service
public class ConsumerService {
	private Logger logger;

	public ConsumerService() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	/*
	 * @KafkaListener(topics = "${kafka.sample.topic}") public void
	 * secondTopicListener(Bank payload) {
	 * logger.info("received json payload = {}",
	 * JacksonUtil.getToString(payload)); }
	 */

	/*
	 * @KafkaListener(topics = "${kafka.sample.topic}") public void
	 * receive(@Payload Bank data, @Headers MessageHeaders headers) {
	 * logger.info("received payload ='{}'", data);
	 * 
	 * headers.keySet().forEach(key -> { logger.info("{}: {}", key,
	 * headers.get(key)); }); }
	 */

	@KafkaListener(topics = "${kafka.sample.topic}")
	public void receiveMessage(@Payload List<Bank> messages,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
			@Header(KafkaHeaders.OFFSET) List<Long> offsets) {
		logger.info("beginning to consume batch messages");
		for (int i = 0; i < messages.size(); i++) {
			logger.info("received message='{}' with partition-offset='{}'", messages.get(i),
					partitions.get(i) + "-" + offsets.get(i));

		}
		logger.info("all batch messages consumed");
	}

}
