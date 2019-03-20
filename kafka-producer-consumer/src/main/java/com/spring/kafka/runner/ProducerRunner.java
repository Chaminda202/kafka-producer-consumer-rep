package com.spring.kafka.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spring.kafka.service.ProducerService;

@Component
public class ProducerRunner implements CommandLineRunner{
	private Logger logger;
	private ProducerService producerService;
	@Value("${kafka.sensor.topic}")
	private String sensorTopic;
	
	@Value("${kafka.sample.topic}")
	private String sampleTopic;
	
	public ProducerRunner(ProducerService producerService){
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.producerService = producerService;
	}

	/*@Override
	public void run(String... args) throws Exception {
		logger.info("Starts producing data");
		for(int i = 0; i < 10 ; i++)
			producerService.send(sensorTopic, "SSP"+i, "data ssp "+i);
		
		for(int i = 0; i < 10 ; i++)
			producerService.send(sensorTopic, "TSS", "data tss "+i);
		
		logger.info("finished producing data");
	}*/
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("Starts producing data");
		for(int i = 0; i < 100 ; i++)
			producerService.send(sampleTopic, "Hello world ... "+i);
		logger.info("finished producing data");
	}
}
