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
@RequestMapping(value = "/javainuse-rabbitmq/direct/")
public class RabbitMQDirectWebController {

	@Autowired
	private AmqpTemplate amqpTemplate;

        //http://localhost:8080/javainuse-rabbitmq/direct/producer?exchangeName=direct-exchange&routingKey=admin&messageData=HelloWorldJavaInUse
	@GetMapping(value = "/producer")
	public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
			@RequestParam("messageData") String messageData) {

		amqpTemplate.convertAndSend(exchange, routingKey, messageData);

		return "Message sent to the RabbitMQ Successfully";
	}
}
