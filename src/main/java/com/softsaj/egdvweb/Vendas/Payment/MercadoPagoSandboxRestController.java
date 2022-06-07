/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment;

/**
 *
 * @author Marcos
 */

import com.mercadopago.exceptions.MPException;
import com.softsaj.egdvweb.Vendas.Payment.models.AutenticacionResponse;
import com.softsaj.egdvweb.Vendas.Payment.services.AutenticacionResponseService;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin("http://localhost:4200")
@RestController
public class MercadoPagoSandboxRestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PreferenceService preferenceService;
    
    @Autowired
    private AutenticacionResponseService AutenticacionResponseService;

    public MercadoPagoSandboxRestController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }
    

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPreference(
            @RequestBody NewPreferenceDTO preferenceDTO
            ) throws MPException {
        return this.preferenceService.create(preferenceDTO);
    }
    
     @PostMapping(value = "/create/Vendedor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPreferenceVendedor(
            @RequestBody NewPreferenceDTO preferenceDTO,
            @RequestParam("id") Long id
            ) throws MPException {
        return this.preferenceService.createVendedor(preferenceDTO, id);
    }
    
     @PostMapping(value = "/create/VendedorFee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPreferenceVendedorFee(
            @RequestBody NewPreferenceDTO preferenceDTO
            ) throws MPException {
        return this.preferenceService.createVendedorFee(preferenceDTO);
    }
    
    
     @PostMapping("/create/add")
    public ResponseEntity<AutenticacionResponse> createAutorizationCode(
            @RequestBody AutenticacionResponse autenticacionresponse,
            @RequestParam("id") Long id) {
        
        System.out.println(id);
        System.out.println(autenticacionresponse);
        
        autenticacionresponse.setState(id.toString());
        autenticacionresponse.setId(id);
       
        AutenticacionResponse newautenticacionresponse = AutenticacionResponseService.addAutenticacionResponse(autenticacionresponse);
        
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/venda/{id}").buildAndExpand(newautenticacionresponse.getId()).toUri();
        
        return new ResponseEntity<>(newautenticacionresponse, HttpStatus.CREATED);
    }
    
     @GetMapping("/auth/{id}")
    public ResponseEntity<AutenticacionResponse> getCienfiloById (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
       
        AutenticacionResponse resultpago = AutenticacionResponseService.findAutenticacionResponseById(id);
        return new ResponseEntity<>(resultpago, HttpStatus.OK);
    }
    
    /*State: APROVED
Type: Mastercard
Number:    5031755734530604
CVV: 123
Expire at: 11/25
Holder: APRO GOMEZ
DNI: 31256588
Email: apro_gomez@gmail.com
---------------------------------
State: REJECTED
Type: Mastercard
Number:    5031755734530604
CVV: 123
Expire at: 11/25
Holder: EXPI GOMEZ
DNI: 31256588
Email: expi_gomez@gmail.com*/
}
