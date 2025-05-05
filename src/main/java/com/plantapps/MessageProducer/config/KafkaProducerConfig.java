package com.plantapps.MessageProducer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
	
	private final Map<String, Map<String, Object>> tenantProducerConfigs = new HashMap<>();
	
	// Method to build Kafka producer config for a given tenant
    private Map<String, Object> buildProducerConfig(String tenantID) {
        String bootstrapServers = env.getProperty("spring.kafka.producer." + tenantID + ".bootstrap-servers");
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }
    
    // Create or fetch a producer configuration for the given tenant
    public Map<String, Object> getProducerConfig() {
    	String tenantID = TenantContext.getTenantId();
        if (!tenantProducerConfigs.containsKey(tenantID)) {
            // Build and cache the configuration for the tenant
            tenantProducerConfigs.put(tenantID, buildProducerConfig(tenantID));
        }
        return tenantProducerConfigs.get(tenantID);
    }
	
	@Bean
	@Lazy
	public ProducerFactory<String, Object> factory()
	{
		Map<String, Object> producerConfig = getProducerConfig();
		return new DefaultKafkaProducerFactory<>(producerConfig);
	}
	
	@Bean
	@Lazy
	public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> factory)
	{
		return new KafkaTemplate<>(factory);
	}
	
}
