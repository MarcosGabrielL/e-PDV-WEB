/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.RabbitMQ.Controllers;

/**
 *
 * @author Marcos
 */
import com.softsaj.egdvweb.Vendas.models.RequestWrapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/egdvweb-rabbitmq/direct/")
public class RabbitMQDirectWebController {

	@Autowired
	private AmqpTemplate amqpTemplate;

        //http://localhost:8080/javainuse-rabbitmq/direct/producer?exchangeName=direct-exchange&routingKey=admin&messageData=HelloWorldJavaInUse
	@GetMapping(value = "/venda-producer")
	public ResponseEntity<Vendas> producer(@RequestBody RequestWrapper requestWrapper) throws JsonProcessingException {
            
                 Gson gson = new Gson();
		 Vendas venda = gson.fromJson( new String((String) amqpTemplate.convertSendAndReceive("direct-exchange", "admin", mapToJson(requestWrapper))), 
                         Vendas.class);
		return new ResponseEntity<>(venda, HttpStatus.CREATED);
	}
        
        
        
   protected String mapToJson(Object obj) throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
   }
}
