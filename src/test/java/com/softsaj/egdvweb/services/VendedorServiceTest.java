/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.services;

import com.softsaj.egdvweb.Abstract.AbstractTest;
import com.softsaj.egdvweb.repositories.VendedorRepository;
import javax.ws.rs.core.Application;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
 
/**
 *
 * @author Marcos
 */

@ExtendWith(MockitoExtension.class)
public class VendedorServiceTest extends AbstractTest {
    
    
    @Mock
    @Autowired
    private VendedorRepository vendedorRepo;
 
    @Mock
    @Autowired
    private VendedorService vendedorService;
 
    @BeforeEach 
    public void setUp(){
        
        super.setUp();
        this.vendedorService = new VendedorService();
        
    }
 
    @org.junit.Test
   public void getAllVendedor(){
       
        
        assertThat(vendedorService.findAll());
   }
    
}
