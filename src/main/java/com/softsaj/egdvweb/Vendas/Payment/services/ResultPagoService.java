/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.services;

import com.softsaj.egdvweb.Vendas.Payment.models.ResultPago;
import com.softsaj.egdvweb.Vendas.Payment.repositories.ResultPagoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
@Service
public class ResultPagoService {
    
 @Autowired
    private ResultPagoRepository rp;
    
     public List<ResultPago> findAll() {
        return rp.findAll();
    }
     
     public ResultPago findResultPagoById(Long id) {
        return rp.findResultPagoById(id);
    }
    
     public List<ResultPago> userResultPago(String id) {
        return rp.userResultPago(id);
    }
    
     
     public ResultPago addResultPago(ResultPago cinefilo) {
        return rp.save(cinefilo);
    }
     
      public ResultPago updateResultPago(ResultPago cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteResultPago(Long id){
        try{
          rp.deleteResultPagoById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o ResultPago");
        }
    }
      
}

