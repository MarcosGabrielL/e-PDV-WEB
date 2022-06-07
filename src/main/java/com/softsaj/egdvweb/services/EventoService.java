/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.softsaj.egdvweb.models.Evento;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;


/**
 *
 * @author Marcos
 */
public class EventoService {
    
   
    
     public static void SaveEvento(Evento evento,String token) throws IOException, InterruptedException{
                
            String apiHost = "https://emiele-service-gerenciador.herokuapp.com/eventos/evento/add?token=1";
       
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(apiHost);

            // Request parameters and other properties.
            //List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(2);
            //params.add(new BasicNameValuePair("token", token));
            //httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            
           // httppost.setEntity(evento);
           ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("target/car.json"), evento);
            String JSON_STRING = objectMapper.writeValueAsString(evento);
            
            StringEntity requestEntity = new StringEntity(
            JSON_STRING,
            ContentType.APPLICATION_JSON);

        httppost.setEntity(requestEntity);

            

            //Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            // the response:
            System.out.println("Response"+response.getEntity().getContentEncoding());
 
        }
    
}
