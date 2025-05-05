package com.plantapps.MessageProducer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig
{
//	@Value("${spring.kafka.consumer.bootstrap-servers}")
//	private String bootrapServer;
	@Autowired
	private Environment env;
	
	@Bean
	public Map<String, Object> producerConfig()
	{
		String property = env.getProperty("spring.kafka.producer.bootstrap-servers");
		Map<String, Object> map = new HashMap<>();
		map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, property);
		map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return map;
	}
	
	@Bean
	public ProducerFactory<String, Object> factory()
	{
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> factory)
	{
		return new KafkaTemplate<>(factory);
	}
	
}
