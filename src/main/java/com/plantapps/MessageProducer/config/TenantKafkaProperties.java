package com.plantapps.MessageProducer.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "kafka.producer")
@Component
public class TenantKafkaProperties
{
	private Map<String, TenantKafkaConfig> configs = new HashMap<>();

	public Map<String, TenantKafkaConfig> getConfigs()
	{
		return configs;
	}

	public void setConfigs(Map<String, TenantKafkaConfig> configs)
	{
		this.configs = configs;
	}

	public TenantKafkaConfig getConfigForTenant(String tenantId)
	{
		return configs.get(tenantId);
	}
}
