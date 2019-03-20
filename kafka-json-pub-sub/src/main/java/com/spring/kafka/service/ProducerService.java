package com.spring.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.spring.kafka.model.Bank;
import com.spring.kafka.util.JacksonUtil;

@Service
public class ProducerService {
	private Logger logger;
	private KafkaTemplate<String, Bank> kafkaTemplate;
	@Value("${kafka.sample.topic}")
	private String kafkaTopic;

	public ProducerService(KafkaTemplate<String, Bank> kafkaTemplate) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.kafkaTemplate = kafkaTemplate;
	}
	
	/*public void send(String topic, Bank data) {
		logger.info("sending json payload {}", JacksonUtil.getToString(data));
		ListenableFuture<SendResult<String, Bank>> future = kafkaTemplate.send(topic, data);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Bank>>() {

			@Override
			public void onSuccess(SendResult<String, Bank> result) {
				logger.info("sending json payload {} -> {}", JacksonUtil.getToString(data), result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("sending json payload {} -> {}", JacksonUtil.getToString(data), ex.getMessage());
			}
		});
	}*/
	
	public void send(String topic, Bank data) {
		logger.info("sending json payload {}", JacksonUtil.getToString(data));
		
		Message<Bank> payload = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
		ListenableFuture<SendResult<String, Bank>> future = kafkaTemplate.send(payload);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Bank>>() {

			@Override
			public void onSuccess(SendResult<String, Bank> result) {
				logger.info("sending json payload {} -> {}", JacksonUtil.getToString(data), result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("sending json payload {} -> {}", JacksonUtil.getToString(data), ex.getMessage());
			}
		});
	}
}
