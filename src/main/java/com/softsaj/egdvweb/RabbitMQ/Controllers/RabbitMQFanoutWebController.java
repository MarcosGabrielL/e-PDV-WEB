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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/javainuse-rabbitmq/fanout/")
public class RabbitMQFanoutWebController {

	@Autowired
	private AmqpTemplate amqpTemplate;
        
        /**
         * 
         * We send the message using the url - http://localhost:8080/javainuse-rabbitmq/fanout/producer?exchangeName=fanout-exchange&messageData=HelloWorldJavaInUse
            exchange name= "fanout-exchange"
            message to sent to queue = "HelloWorldJavaInUse"
         * 
         * @param exchange
         * @param messageData
         * @return 
         */

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("exchangeName") String exchange,
			@RequestParam("messageData") String messageData) {

		amqpTemplate.convertAndSend(exchange, "", messageData);

		return "Message sent to the RabbitMQ Fanout Exchange Successfully";
	}
}
