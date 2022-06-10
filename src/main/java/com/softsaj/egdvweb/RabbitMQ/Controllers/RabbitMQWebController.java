/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.RabbitMQ.Controllers;

/**
 *
 * @author Marcos
 */

import com.softsaj.egdvweb.RabbitMQ.Services.RabbitMQSender;
import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.models.RequestWrapper;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/javainuse-rabbitmq/")
public class RabbitMQWebController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	@GetMapping(value = "/producer")
	public String producer(@RequestBody RequestWrapper requestWrapper) {
	
	
	Vendas venda =  requestWrapper.getVendas();
       List<Produto> produtos = requestWrapper.getProdutos();
       
        
        Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale);
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date d = new Date();
                String data = formatador.format(d.getTime());
                System.out.println("DATA: "+data);
                //Salva venda e pega id venda
                
        venda.setDiavenda(formatador1.format(d.getTime()));
        venda.setStatus("0");
        venda.setDatavenda(data);
        
		rabbitMQSender.send(venda);

		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}
        
}


