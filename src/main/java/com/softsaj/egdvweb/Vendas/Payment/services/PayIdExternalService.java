/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.services;

/**
 *
 * @author Marcos
 */
import com.softsaj.egdvweb.Vendas.Payment.models.PayIdExternal;
import com.softsaj.egdvweb.Vendas.Payment.repositories.PayIdExternalRepository;
import java.util.List;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
/**
 *
 * @author Marcos
 */
@Service
public class PayIdExternalService {
 @Autowired
    private PayIdExternalRepository rp;
    
     public List<PayIdExternal> findAll() {
        return rp.findAll();
    }
     
     public PayIdExternal findPayIdExternalById(Long id) {
        return rp.findPayIdExternalById(id);
    }
    
     public List<PayIdExternal> userPayIdExternal(String id) {
        return rp.userPayIdExternal(id);
    }
    
     
     public PayIdExternal addPayIdExternal(PayIdExternal cinefilo) {
        return rp.save(cinefilo);
    }
     
      public PayIdExternal updatePayIdExternal(PayIdExternal cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deletePayIdExternal(Long id){
        try{
          rp.deletePayIdExternalById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o PayIdExternal");
        }
    }
      
}

