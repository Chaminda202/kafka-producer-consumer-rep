package com.spring.kafka.runner;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spring.kafka.model.Bank;
import com.spring.kafka.service.ProducerService;

//@Component
public class ProducerRunner implements CommandLineRunner{
	private Logger logger;
	private ProducerService producerService;
		
	@Value("${kafka.sample.topic}")
	private String sampleTopic;
	
	public ProducerRunner(ProducerService producerService){
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.producerService = producerService;
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Starts producing data");
		Bank bank = null;
		for(int i = 0; i < 100 ; i++){
			bank = new Bank();
			bank.setBankName("Commercial Bank");
			bank.setBankCode("AAA_"+i);
			bank.setNumberOfBranch(i+1);
			bank.setStartedDate(LocalDate.now().minusDays(i).toString());
			producerService.send(sampleTopic, bank);
		}	
		logger.info("finished producing data");
	}
}
