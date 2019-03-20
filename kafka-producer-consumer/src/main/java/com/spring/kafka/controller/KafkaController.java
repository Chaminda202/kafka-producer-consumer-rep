package com.spring.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.kafka.request.KafkaRequest;
import com.spring.kafka.service.ProducerService;
import com.spring.kafka.util.JacksonUtil;

@RestController
public class KafkaController {

	private Logger logger;
	private ProducerService producerService;
	
	public KafkaController(ProducerService producerService){
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.producerService = producerService;
	}
	
	@RequestMapping(value = "/publish", method=RequestMethod.POST)
	public void sendMessageToKafkaTopic(@RequestBody KafkaRequest kafkaRequest){
		String payload = JacksonUtil.getToString(kafkaRequest);
		logger.info("{} -> {}", "SEND_MESSAGE_TO_KAFKA_TOPIC", payload);
		producerService.send(payload);
		logger.info("{} -> {}", "SEND_MESSAGE_TO_KAFKA_TOPIC", "SUCCESS");
	}
}
