package com.spring.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.spring.kafka.model.Bank;

@Configuration
@EnableKafka
public class ConsumerConfig {

	@Value("${kafka.bootstrap.servers}")
	private String bootstrapServers;
	@Value("${kafka.group.id}")
	private String groupId;
	@Value("${kafka.auto.offset.reset}")
	private String autoOffsetRest;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				JsonDeserializer.class);
		props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetRest);
		props.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 8);
		return props;
	}

	@Bean
	public ConsumerFactory<String, Bank> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(),
				new StringDeserializer(),
                new JsonDeserializer<>(Bank.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Bank> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String,  Bank> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		//enables=d batch processing
		factory.setBatchListener(true);
		return factory;
	}
}
