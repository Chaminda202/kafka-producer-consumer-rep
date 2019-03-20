package com.spring.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.spring.kafka.util.JacksonUtil;

@Service
public class ProducerService {
	private Logger logger;
	private KafkaTemplate<String, Object> kafkaTemplate;
	@Value("${kafka.sample.topic}")
	private String kafkaTopic;

	public ProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void send(String topic, Object data) {
		logger.info("sending json payload {}", JacksonUtil.getToString(data));
		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, data);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

			@Override
			public void onSuccess(SendResult<String, Object> result) {
				logger.info("sending json payload {} -> {}", JacksonUtil.getToString(data), result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("sending json payload {} -> {}", JacksonUtil.getToString(data), ex.getMessage());
			}
		});
	}
	
	public void send(String topic, String key, Object data) {
		logger.info("sending json payload {}", JacksonUtil.getToString(data));
		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, data);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

			@Override
			public void onSuccess(SendResult<String, Object> result) {
				logger.info("sending json payload {} -> {}", JacksonUtil.getToString(data), result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("sending json payload {} -> {}", JacksonUtil.getToString(data), ex.getMessage());
			}
		});
	}
}
