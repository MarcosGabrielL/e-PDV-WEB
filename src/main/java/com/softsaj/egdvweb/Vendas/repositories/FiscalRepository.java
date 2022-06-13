/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.Vendas.repositories;

import com.softsaj.egdvweb.Vendas.models.Fiscal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface FiscalRepository extends JpaRepository<Fiscal, Long> {
    
     Optional<Fiscal> findFiscalById(Integer id);
     
     void deleteFiscalById(Integer id);
}
