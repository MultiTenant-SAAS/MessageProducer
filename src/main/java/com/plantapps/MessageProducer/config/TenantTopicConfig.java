package com.plantapps.MessageProducer.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-topic")
public class TenantTopicConfig
{
	private Map<String, String> topicMap;

	public Map<String, String> getTopicMap()
	{
		return topicMap;
	}

	public void setTopicMap(Map<String, String> topicMap)
	{
		this.topicMap = topicMap;
	}
}
