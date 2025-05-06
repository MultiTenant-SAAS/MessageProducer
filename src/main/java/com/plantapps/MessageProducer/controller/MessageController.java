package com.plantapps.MessageProducer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.plantapps.MessageProducer.kafka.KafkaMessageProducer;
import com.plantapps.MessageProducer.model.Message;

@RestController
public class MessageController
{
	@Autowired
	private KafkaMessageProducer producer;
	
	@PostMapping("/message")
	public void produceMessageData(@RequestHeader("tid") String tid, @RequestBody Message message)
	{
		//Message messsage  = new Message(1L, data);
		producer.produceMesageTenents(tid , message);
	}
}
