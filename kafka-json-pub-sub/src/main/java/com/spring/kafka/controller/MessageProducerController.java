package com.spring.kafka.controller;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.kafka.model.MessagePayload;
import com.spring.kafka.service.ProducerService;

@RestController
public class MessageProducerController {
	private final Logger logger;
	private final ProducerService producerService;
	private CountDownLatch countDownLatch;
	@Value("${kafka.messages.per.request}")
	private int messagesPerRequest;
	@Value("${kafka.sample.topic}")
	private String kafkaTopic;

	public MessageProducerController(ProducerService producerService){
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.producerService = producerService;
	}
	
	@GetMapping("/produce/message")
	public String produceMessage(){
		countDownLatch = new CountDownLatch(messagesPerRequest);
		 IntStream.range(0, messagesPerRequest).forEach(i -> {
			 producerService.send(kafkaTopic, String.valueOf(i), 
					 new MessagePayload("Hi, How are you?", i));
		 });
		 try {
			countDownLatch.await(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("{} -> waited seconds {}", "Thread_Interrupted", "30");
			Thread.currentThread().interrupt();
		}
		 logger.info("All messages are received Successfully...!!!");
		return "Successfully produce the message...!!!";
	}
}
