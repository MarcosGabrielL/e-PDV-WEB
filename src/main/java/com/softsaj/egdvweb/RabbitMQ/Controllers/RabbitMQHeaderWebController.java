/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.RabbitMQ.Controllers;

/**
 *
 * @author Marcos
 */

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/javainuse-rabbitmq/header/")
public class RabbitMQHeaderWebController {

	@Autowired
	private AmqpTemplate amqpTemplate;
        
        /**
         * We send the message using the url - http://localhost:8080/javainuse-rabbitmq/header/producer?exchangeName=header-exchange&department=admin&messageData=HelloWorldJavaInUse
            exchange name= "header-exchange"
            header key ="admin"
            message to be sent to queue = "HelloWorldJavaInUse"
         * @param exchange
         * @param department
         * @param messageData
         * @return 
         */

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("department") String department,
			@RequestParam("messageData") String messageData) {

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("department", department);
		MessageConverter messageConverter = new SimpleMessageConverter();
		Message message = messageConverter.toMessage(messageData, messageProperties);
		amqpTemplate.send(exchange, "", message);

		return "Message sent to the RabbitMQ Header Exchange Successfully";
	}    
}
