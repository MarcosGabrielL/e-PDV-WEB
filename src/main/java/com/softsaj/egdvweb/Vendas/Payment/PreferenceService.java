/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment;

/**
 *
 * @author Marcos
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.OAuth;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.softsaj.egdvweb.Vendas.Payment.models.PayIdExternal;
import com.softsaj.egdvweb.Vendas.Payment.services.PayIdExternalService;
import com.softsaj.egdvweb.Vendas.Payment.services.RootService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PreferenceService {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

     @Autowired
    private PayIdExternalService vs;

    public ResponseEntity create(NewPreferenceDTO preferenceDTO) throws MPException {
       /* if (StringUtils.isEmpty(preferenceDTO.getAccessToken())) {
            return ResponseEntity.badRequest().body("Access token is mandatory");
        }
        if (preferenceDTO.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Items empty");
        }*/

        MercadoPago.SDK.setAccessToken(preferenceDTO.getAccessToken());
        String notificationUrl = "https://emiele-service-gerenciador.herokuapp.com/generic";

        Preference p = new Preference();
        
        PayIdExternal payid = new PayIdExternal();
        payid.setType("");
        payid = vs.addPayIdExternal(payid);
        p.setExternalReference(payid.getId().toString());
        
        p.setBackUrls(
          new BackUrls().setSuccess(notificationUrl)
                  .setPending(notificationUrl)
                .setFailure(notificationUrl)
        );
        p.setItems(preferenceDTO.getItems().stream()
                .map(i -> {
                    Item item = new Item();
                    item.setUnitPrice(i.getPrice());
                    item.setTitle(i.getName());
                    item.setQuantity(i.getQuantity());

                    return item;
                })
                .collect(Collectors.toCollection(ArrayList::new)));
            p.save();
         
          if (StringUtils.isEmpty(p.getId())) {
            return ResponseEntity.status(404).body(
                    Collections.singletonMap("Message",
                            "Preference was not created. Check if Access Token is valid")
            );
        }
        return ResponseEntity.ok(gson.toJson(p));
    }
    
   
    public ResponseEntity createVendedor(NewPreferenceDTO preferenceDTO, Long id) throws MPException {
       /* if (StringUtils.isEmpty(preferenceDTO.getAccessToken())) {
            return ResponseEntity.badRequest().body("Access token is mandatory");
        }
        if (preferenceDTO.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Items empty");
        }*/

        MercadoPago.SDK.setAccessToken(preferenceDTO.getAccessToken());
        String notificationUrl = "https://emiele-service-gerenciador.herokuapp.com/generic/Vendedor";

        Preference p = new Preference();
        
        //Id da venda
        p.setExternalReference(id.toString());
        
        p.setBackUrls(
          new BackUrls().setSuccess(notificationUrl)
                  .setPending(notificationUrl)
                .setFailure(notificationUrl)
        );
        p.setItems(preferenceDTO.getItems().stream()
                .map(i -> {
                    Item item = new Item();
                    item.setUnitPrice(i.getPrice());
                    item.setTitle(i.getName());
                    item.setQuantity(i.getQuantity());

                    return item;
                })
                .collect(Collectors.toCollection(ArrayList::new)));
        float a = 6;
        p.setMarketplaceFee(a);
            p.save();
         
          if (StringUtils.isEmpty(p.getId())) {
            return ResponseEntity.status(404).body(
                    Collections.singletonMap("Message",
                            "Preference was not created. Check if Access Token is valid")
            );
        }
        return ResponseEntity.ok(gson.toJson(p));
    }
    
    public ResponseEntity createVendedorFee(NewPreferenceDTO preferenceDTO) throws MPException {
       /* if (StringUtils.isEmpty(preferenceDTO.getAccessToken())) {
            return ResponseEntity.badRequest().body("Access token is mandatory");
        }
        if (preferenceDTO.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Items empty");
        }*/

        MercadoPago.SDK.setAccessToken(preferenceDTO.getAccessToken());
        String notificationUrl = "https://emiele-service-gerenciador.herokuapp.com/generic";

        Preference p = new Preference();
        
        PayIdExternal payid = new PayIdExternal();
        //Id da venda
        payid.setType("");
        payid = vs.addPayIdExternal(payid);
        p.setExternalReference(payid.getId().toString());
        
        p.setBackUrls(
          new BackUrls().setSuccess(notificationUrl)
                  .setPending(notificationUrl)
                .setFailure(notificationUrl)
        );
        p.setItems(preferenceDTO.getItems().stream()
                .map(i -> {
                    Item item = new Item();
                    item.setUnitPrice(i.getPrice());
                    item.setTitle(i.getName());
                    item.setQuantity(i.getQuantity());

                    return item;
                })
                .collect(Collectors.toCollection(ArrayList::new)));
       
            p.save();
         
          if (StringUtils.isEmpty(p.getId())) {
            return ResponseEntity.status(404).body(
                    Collections.singletonMap("Message",
                            "Preference was not created. Check if Access Token is valid")
            );
        }
        return ResponseEntity.ok(gson.toJson(p));
    }
    
}
