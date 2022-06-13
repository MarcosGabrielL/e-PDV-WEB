/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.Vendas.services;

import com.softsaj.egdvweb.Vendas.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.models.Fiscal;
import com.softsaj.egdvweb.Vendas.repositories.FiscalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
 @Service
public class FiscalService {
      
      
      @Autowired
    private FiscalRepository rp;
    
     public List<Fiscal> findAll() {
        return rp.findAll();
    }
     
     public Fiscal findCienfiloById(Integer id) {
        return rp.findFiscalById(id)
                .orElseThrow(() -> new UserNotFoundException("Fiscal by id " + id + " was not found"));
    }
     
     public Fiscal addFiscal(Fiscal cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Fiscal updateFiscal(Fiscal cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteFiscal(Integer id){
        try{
          rp.deleteFiscalById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Fiscal");
        }
    }
      
  }
