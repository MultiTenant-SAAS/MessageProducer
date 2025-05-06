package com.plantapps.MessageProducer.kafka;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.plantapps.MessageProducer.config.KafkaTemplateManager;
import com.plantapps.MessageProducer.config.TenantKafkaProperties;
import com.plantapps.MessageProducer.model.Message;

@Service
public class KafkaMessageProducer
{
	private final KafkaTemplateManager kafkaTemplateManager;
    private final TenantKafkaProperties tenantKafkaProperties;
    
	public KafkaMessageProducer(KafkaTemplateManager kafkaTemplateManager,
            TenantKafkaProperties tenantKafkaProperties)
	{
		this.kafkaTemplateManager = kafkaTemplateManager;
        this.tenantKafkaProperties = tenantKafkaProperties;
	}
	
	public void produceMesageTenents(String tid, Message message)
	{
		RecordHeaders headers = new RecordHeaders();
		headers.add(new RecordHeader("tenantId", tid.getBytes()));
		ProducerRecord<String, Message> producerRecord = null;

		KafkaTemplate<String, Message> template = kafkaTemplateManager.getKafkaTemplate(tid);
        String topic = tenantKafkaProperties.getConfigForTenant(tid).getTopic();
		
		if("tenant-1".equals(tid))
		{
//			producerRecord = new ProducerRecord<String, Object>("site_1", null, null,
//					message, headers);
			producerRecord = new ProducerRecord<String, Message>(topic, null, null,
					message, headers);
		}
		else if("tenant-2".equals(tid))
		{
//			producerRecord = new ProducerRecord<String, Object>("site_2", null, null,
//					message, headers);
			producerRecord = new ProducerRecord<String, Message>(topic, null, null,
					message, headers);
		}
		CompletableFuture<SendResult<String, Message>> send = template.send(producerRecord);
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
