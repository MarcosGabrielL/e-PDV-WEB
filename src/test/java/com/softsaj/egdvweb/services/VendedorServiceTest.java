/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.services;

import com.softsaj.egdvweb.repositories.VendedorRepository;
import static org.mockito.Mockito.verify;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
/**
 *
 * @author Marcos
 */

@ExtendWith(MockitoExtension.class)
public class VendedorServiceTest {
    
    @Mock private VendedorRepository vendedorRepo;
 
    private VendedorService vendedorService;
 
    @BeforeEach void setUp()
    {
        this.vendedorService = new VendedorService();
    }
 
    @Test void getAllVendedor()
    {
        vendedorService.findAll();
        verify(vendedorRepo).findAll();
    }
    
}
