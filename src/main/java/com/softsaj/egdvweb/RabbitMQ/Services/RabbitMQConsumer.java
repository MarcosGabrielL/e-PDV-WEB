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
import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.models.RequestWrapper;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

@Slf4j
@Component
public class RabbitMQConsumer {
 
	@RabbitListener(queues = "adminQueue")
	public void recievedMessage( Message message) throws InvalidDateException {
            
             log.info("Recieved Message From RabbitMQ ='{}' ", message);
		
	}   
        
        
        
}
