/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.util;

import lombok.Value;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 *
 * @author Marcos
 */
@Service
public class validateToken {
    
   
    @Autowired
public RestTemplate restTemplate;
 

private String apiHost = "https://emiele-service-security.herokuapp.com/islogged?token=";
 



public boolean isLogged(String token)
{
    //System.err.println("token:"+token);
    
    RestTemplate restTemplate = new RestTemplate();
      return restTemplate.getForObject(apiHost+token, Boolean.class);

}







/*getForObject(url, classType) – recupera uma representação fazendo um GET na URL. A resposta (se houver) é desempacotada para determinado tipo de classe e retornada.
getForEntity(url, responseType) – recupera uma representação como ResponseEntity fazendo um GET na URL.
exchange(url, httpMethod, requestEntity, responseType) – executa o especificado RequestEntitye retorna a resposta como ResponseEntity .
execute(url, httpMethod, requestCallback, responseExtractor) – execute o httpMethod para o modelo de URI fornecido, preparando a solicitação com RequestCallback e lendo a resposta com ResponseExtractor .*/
    
}
