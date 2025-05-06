package com.plantapps.MessageProducer.config;

public class TenantKafkaConfig
{
	private String bootstrapServer;
    private String topic;
	public String getBootstrapServer()
	{
		return bootstrapServer;
	}
	public void setBootstrapServer(String bootstrapServer)
	{
		this.bootstrapServer = bootstrapServer;
	}
	public String getTopic()
	{
		return topic;
	}
	public void setTopic(String topic)
	{
		this.topic = topic;
	}
    
}
