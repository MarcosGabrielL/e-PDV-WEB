/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.RabbitMQ.Services;

/**
 *
 * @author Marcos
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQListner implements MessageListener {

	public void onMessage(Message message) {
            log.info("Consuming Message='{}' ", new String(message.getBody()));
            //System.out.println("Consuming Message='{}' " + new String(message.getBody()));
		
	}

}
