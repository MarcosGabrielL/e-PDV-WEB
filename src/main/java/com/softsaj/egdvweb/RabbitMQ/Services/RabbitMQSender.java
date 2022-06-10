/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.RabbitMQ.Services;

/**
 *
 * @author Marcos
 */
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softsaj.egdvweb.Vendas.models.Vendas;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${javainuse.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${javainuse.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Vendas company) {
		rabbitTemplate.convertAndSend(exchange, routingkey, company);
                   log.info("send message='{}' ", company);
           
	    
	}  
}
