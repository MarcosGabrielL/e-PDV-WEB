/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.RabbitMQ.Config;

/**
 *
 * @author Marcos
 */

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQFanoutConfig {

	@Bean
	Queue marketingQueue() {
		return new Queue("marketingQueue", false);
	}

	@Bean
	Queue financeQueue() {
		return new Queue("financeQueue", false);
	}

	@Bean
	Queue adminQueue() {
		return new Queue("adminQueue", false);
	}

	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange("fanout-exchange");
	}

	@Bean
	Binding marketingBinding(Queue marketingQueue, FanoutExchange exchange) {
		return BindingBuilder.bind(marketingQueue).to(exchange);
	}

	@Bean
	Binding financeBinding(Queue financeQueue, FanoutExchange exchange) {
		return BindingBuilder.bind(financeQueue).to(exchange);
	}

	@Bean
	Binding adminBinding(Queue adminQueue, FanoutExchange exchange) {
		return BindingBuilder.bind(adminQueue).to(exchange);
	}
   
}
