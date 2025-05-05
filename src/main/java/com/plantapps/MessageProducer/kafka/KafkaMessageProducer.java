package com.plantapps.MessageProducer.kafka;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.plantapps.MessageProducer.config.KafkaProducerConfig;
import com.plantapps.MessageProducer.config.TenantContext;
import com.plantapps.MessageProducer.config.TenantTopicConfig;
import com.plantapps.MessageProducer.model.Message;

@Service
public class KafkaMessageProducer
{
	//private KafkaTemplate<String, Object> kafkaTemplate;
	private KafkaProducerConfig config;
	
	private TenantTopicConfig topicConfig;
	
	public KafkaMessageProducer(KafkaProducerConfig config, TenantTopicConfig topicConfig)
	{
		this.config = config;
		this.topicConfig = topicConfig;
	}
	
	public void produceMesageTenents(String tid, Message message)
	{
		// Set tenant ID in the TenantContext
        TenantContext.setTenantId(tid);
		RecordHeaders headers = new RecordHeaders();
		headers.add(new RecordHeader("tenantId", tid.getBytes()));
		ProducerRecord<String, Object> producerRecord = null;
		Map<String, String> topicMap = topicConfig.getTopicMap(); //added new
		if("tenant-1".equals(tid))
		{
//			producerRecord = new ProducerRecord<String, Object>("site_1", null, null,
//					message, headers);
			producerRecord = new ProducerRecord<String, Object>("site_1", null, null,
					message, headers);
		}
		else if("tenant-2".equals(tid))
		{
//			producerRecord = new ProducerRecord<String, Object>("site_2", null, null,
//					message, headers);
			producerRecord = new ProducerRecord<String, Object>("site_2", null, null,
					message, headers);
		}
		KafkaTemplate<String, Object> kafkaTemplateForTenant = config.kafkaTemplate(config.factory());
		CompletableFuture<SendResult<String, Object>> send = kafkaTemplateForTenant.send(producerRecord);
//		CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send("site_1", message);
		send.whenComplete((result, exception)->{
			if(null==exception)
			{
				System.out.println("=======> partition no: "+result.getRecordMetadata().partition()+
						" offset id: "+result.getRecordMetadata().offset());
			}
			else
			{
				System.out.println("exceptiom=> "+exception.getMessage());
			}
		});
	}
	
}
