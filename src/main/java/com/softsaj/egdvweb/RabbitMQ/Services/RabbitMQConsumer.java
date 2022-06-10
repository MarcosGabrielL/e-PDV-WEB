/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.RabbitMQ.Services;

/**
 *
 * @author Marcos
 */
import com.softsaj.egdvweb.RabbitMQ.Exception.InvalidDateException;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Component
public class RabbitMQConsumer {

	@RabbitListener(queues = "${javainuse.rabbitmq.queue}")
	public void recievedMessage(Vendas employee) throws InvalidDateException {
             log.info("Recieved Message From RabbitMQ ='{}' ", employee);
		if (employee.getDatavenda().isEmpty() || employee.getDatavenda() == null) {
			throw new InvalidDateException();
		}
	}   
        
        
        
}
