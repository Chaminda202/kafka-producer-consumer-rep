package com.spring.kafka.service;

import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.spring.kafka.model.MessagePayload;

@Service
public class ConsumerService {
	private Logger logger;

	public ConsumerService() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@KafkaListener(topics = "${kafka.sample.topic}", groupId = "${spring.kafka.consumer.group.id}", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
	public void listenAsObject(ConsumerRecord<String, MessagePayload> cr, @Payload MessagePayload payload) {
		logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
				typeIdHeader(cr.headers()), payload, cr.toString());
	}

	@KafkaListener(topics = "${kafka.sample.topic}", groupId = "${spring.kafka.consumer.group.id}", clientIdPrefix = "string", containerFactory = "kafkaListenerStringContainerFactory")
	public void listenasString(ConsumerRecord<String, String> cr, @Payload String payload) {
		logger.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
				typeIdHeader(cr.headers()), payload, cr);
	}

	@KafkaListener(topics = "${kafka.sample.topic}", groupId = "${spring.kafka.consumer.group.id}", clientIdPrefix = "bytearray", containerFactory = "kafkaListenerByteArrayContainerFactory")
	public void listenAsByteArray(ConsumerRecord<String, byte[]> cr, @Payload byte[] payload) {
		logger.info("Logger 3 [ByteArray] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
				typeIdHeader(cr.headers()), payload, cr);
	}

	private String typeIdHeader(Headers headers) {
		return StreamSupport.stream(headers.spliterator(), false).filter(header -> header.key().equals("__TypeId__"))
				.findFirst().map(header -> new String(header.value())).orElse("N/A");
	}
}
