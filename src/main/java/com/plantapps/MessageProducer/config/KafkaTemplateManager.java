package com.plantapps.MessageProducer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import com.plantapps.MessageProducer.model.Message;

@Component
public class KafkaTemplateManager
{
	private final TenantKafkaProperties tenantKafkaProperties;
    private final Map<String, KafkaTemplate<String, Message>> templateCache = new HashMap<>();

    @Autowired
    public KafkaTemplateManager(TenantKafkaProperties tenantKafkaProperties) {
        this.tenantKafkaProperties = tenantKafkaProperties;
    }

    public KafkaTemplate<String, Message> getKafkaTemplate(String tenantId) {
        return templateCache.computeIfAbsent(tenantId, this::createKafkaTemplate);
    }

    private KafkaTemplate<String, Message> createKafkaTemplate(String tenantId) {
        TenantKafkaConfig config = tenantKafkaProperties.getConfigForTenant(tenantId);
        if (config == null) {
            throw new IllegalArgumentException("Unknown tenant: " + tenantId);
        }

        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServer());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ProducerFactory<String, Message> factory = new DefaultKafkaProducerFactory<>(producerProps);
        return new KafkaTemplate<>(factory);
    }
	
}
