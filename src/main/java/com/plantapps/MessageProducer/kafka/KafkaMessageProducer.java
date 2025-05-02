package com.plantapps.MessageProducer.kafka;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.converter.KafkaMessageHeaders;
import org.springframework.stereotype.Service;

import com.plantapps.MessageProducer.model.Message;

@Service
public class KafkaMessageProducer
{
	private KafkaTemplate<String, Object> kafkaTemplate;
	public KafkaMessageProducer(KafkaTemplate<String, Object> kafkaTemplate)
	{
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void produceMesageTenents(String tid, Message message)
	{
		RecordHeaders headers = new RecordHeaders();
		headers.add(new RecordHeader("tenantId", tid.getBytes()));
		ProducerRecord<String, Object> producerRecord = null;
		if("tenant-1".equals(tid))
		{
			producerRecord = new ProducerRecord<String, Object>("site_1", null, null,
					message, headers);
		}
		else if("tenant-2".equals(tid))
		{
			producerRecord = new ProducerRecord<String, Object>("site_2", null, null,
					message, headers);
		}
		CompletableFuture<SendResult<String, Object>> send = kafkaTemplate.send(producerRecord);
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
