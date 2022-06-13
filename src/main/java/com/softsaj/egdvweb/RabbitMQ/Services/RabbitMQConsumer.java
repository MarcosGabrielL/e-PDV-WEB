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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.softsaj.egdvweb.Vendas.Relatorios.Evento;
import com.softsaj.egdvweb.Vendas.models.Notification;
import com.softsaj.egdvweb.Vendas.models.Vendidos;
import com.softsaj.egdvweb.Vendas.services.EventoService;
import com.softsaj.egdvweb.Vendas.services.NotificationService;
import com.softsaj.egdvweb.Vendas.services.VendasService;
import com.softsaj.egdvweb.Vendas.services.VendidosService;
import java.io.IOException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;



@Slf4j
@Component
public class RabbitMQConsumer {
    
        @Autowired
        private VendasService vs;
    
        @Autowired
        private VendidosService vds;
        
        @Autowired
        private EventoService es;
      
        @Autowired
        private NotificationService ns;
       
        @Autowired
        RabbitTemplate rabbitTemplate;
        
 
	@RabbitListener(queues = "adminQueue")
	public void recievedMessage( Message message) throws InvalidDateException, JsonMappingException, IOException, JSONException {
            
             log.info("Recieved Message From RabbitMQ ='{}' ", message.getMessageProperties());
            
       
       RequestWrapper requestWrapper = getObject(message);
       Vendas venda =  requestWrapper.getVendas();
       List<Produto> produtos = requestWrapper.getProdutos();
        
        venda = AddVenda(venda);
        addVendidos(venda, produtos);
        salvaEvento("1",venda.getDatavenda(),"Vendas","1","Venda: "+venda.getValor(),venda.getVendedor_id());
	
        //Gerar e enviar NFC-e
        
        Message response = new Message(mapToJson(new String(mapToJson(venda))
                .replaceAll("^\"|\"$", "").replaceAll("\\\\","")).getBytes(),
                message.getMessageProperties());
        
        rabbitTemplate.convertAndSend("",  message.getMessageProperties().getReplyTo() , response);
		
	}   
        
        
        
        /**
         * 
         * @param <T>
         * @param json
         * @param clazz
         * @return
         * @throws JsonParseException
         * @throws JsonMappingException
         * @throws IOException 
         */
         protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);
         }
         /**
          * 
          * @param obj
          * @return
          * @throws JsonProcessingException 
          */
         protected String mapToJson(Object obj) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
         }
         
         /**
          * 
          * @param message
          * @return 
          */
         public RequestWrapper getObject(Message message){
             Gson gson = new Gson();
             String jsonContent = new String(message.getBody()).replaceAll("^\"|\"$", "").replaceAll("\\\\","");
            
             return  gson.fromJson(jsonContent, RequestWrapper.class);
         }
         
         /**
          * 
          * @param venda
          * @return 
          */
         public Vendas AddVenda(Vendas venda){
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
        
        
                
         return vs.addVendas(venda);
         }
         
         /**
          * 
          * @param newVendas
          * @param produtos 
          */
         public void addVendidos(Vendas newVendas, List<Produto> produtos){
             //Salva produtos vendidos
            for(Produto p : produtos){
                Vendidos v = new Vendidos();
                v.setCodigo(p.getCodigo());
                v.setDataSaida(newVendas.getDatavenda());
                v.setIdVenda(newVendas.getId().intValue());
                v.setQuantidade(p.getQuantidade());
                v.setTipo(p.getTipo());
                v.setVendedor_ID(newVendas.getVendedor_id());
                v.setDescrição(p.getDescricao());
                vds.addVendidos(v);
            }
         }
         
          public void salvaEvento(String cod,String date, String info, String level, String message,String usuario){
         
         Evento evento = new Evento();
         evento.setCod("2");
         evento.setDate(date);
         evento.setInfo(info);
         evento.setLevel(level);
         evento.setMessage(message);
         evento.setUsuario(usuario);
         
          es.addEvento(evento);
          
          Notification notification = new Notification();
          notification.setCod("1");
         notification.setDate(date);
         notification.setInfo(info);
         notification.setLevel(level);
         notification.setMessage("Novo Pedido: "+message);
         notification.setUsuario(usuario);
         notification.setIsRead(false);
          
          ns.addNotification(notification);
     }

        
}
