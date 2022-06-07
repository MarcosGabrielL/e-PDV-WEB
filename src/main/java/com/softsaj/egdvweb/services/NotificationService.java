/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softsaj.egdvweb.models.Evento;
import com.softsaj.egdvweb.models.Notification;
import java.io.File;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Marcos
 */
public class NotificationService {
    
    public static void addNotification(Notification notification,String token) throws IOException, InterruptedException{
                
            String apiHost = "https://emiele-service-gerenciador.herokuapp.com/notifications/notification/add?token=1";
       
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(apiHost);

            // Request parameters and other properties.
            //List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(2);
            //params.add(new BasicNameValuePair("token", token));
            //httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            
           // httppost.setEntity(evento);
           ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("target/car.json"), notification);
            String JSON_STRING = objectMapper.writeValueAsString(notification);
            
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
