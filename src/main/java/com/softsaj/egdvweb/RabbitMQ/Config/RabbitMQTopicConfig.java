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
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicConfig {
/*
	@Bean
	Queue topicmarketingQueue() {
		return new Queue("marketingQueue", false);
	}

	@Bean
	Queue topicfinanceQueue() {
		return new Queue("financeQueue", false);
	}

	@Bean
	Queue topicadminQueue() {
		return new Queue("adminQueue", false);
	}

	@Bean
	Queue topicallQueue() {
		return new Queue("allQueue", false);
	}

	@Bean
	TopicExchange topicExchange() {
		return new TopicExchange("topic-exchange");
	}
	
	@Bean
	Binding topicmarketingBinding(Queue marketingQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(marketingQueue).to(topicExchange).with("queue.marketing");
	}
	
	@Bean
	Binding topicfinanceBinding(Queue financeQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(financeQueue).to(topicExchange).with("queue.finance");
	}
	
	@Bean
	Binding topicadminBinding(Queue adminQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(adminQueue).to(topicExchange).with("queue.admin");
	}
	
	@Bean
	Binding topicallBinding(Queue allQueue, TopicExchange topicExchange) {
		return BindingBuilder.bind(allQueue).to(topicExchange).with("queue.*");
	}
*/
}
