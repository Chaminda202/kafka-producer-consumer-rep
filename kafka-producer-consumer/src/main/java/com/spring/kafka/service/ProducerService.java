package com.spring.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class ProducerService {
	private Logger logger;
	private KafkaTemplate<String, String> kafkaTemplate;
	@Value("${kafka.sample.topic}")
	private String kafkaTopic;

	public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.kafkaTemplate = kafkaTemplate;
	}

	public void send(String payload) {
		logger.info("sending payload {}", payload);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTopic, payload);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("sending payload {} -> {} -> {}", payload, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("sending payload {} -> {}", payload, ex.getMessage());
			}
		});
	}
	
	public void send(String topic, String key, String data) {
		logger.info("sending payload {} -> {}", key, data);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, data);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("sending payload {} -> {} -> {}", key, data, result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("sending payload {} -> {} -> {}", key, data, ex.getMessage());
			}
		});
	}
	
	public void send(String topic, String data) {
		logger.info("sending payload {}", data);
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("sending payload {} -> {}", data, result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("sending payload {} -> {}", data, ex.getMessage());
			}
		});
	}
}
