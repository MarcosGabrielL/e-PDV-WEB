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
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDeadLetterConfig {
    /*
    @Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange("deadLetterExchange");
	}
	
	@Bean
	DirectExchange Deadexchange() {
		return new DirectExchange("javainuseExchange");
	}

	@Bean
	Queue dlq() {
		return QueueBuilder.durable("deadLetter.queue").build();
	}

	@Bean
	Queue Deadqueue() {
		return QueueBuilder.durable("javainuse.queue").withArgument("x-dead-letter-exchange", "deadLetterExchange")
				.withArgument("x-dead-letter-routing-key", "deadLetter").build();
	}

	@Bean
	Binding DLQbinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
	}

	@Bean
	Binding Deadbinding() {
		return BindingBuilder.bind(Deadqueue()).to(Deadexchange()).with("javainuse");
	}

	@Bean
	public MessageConverter DeadjsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate DeadrabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(DeadjsonMessageConverter());
		return rabbitTemplate;
	}
*/
}
