/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.Vendas.repositories;

import com.softsaj.egdvweb.Vendas.models.NotaNFE;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface NotaNFERepository extends JpaRepository<NotaNFE, Long> {
    
     Optional<NotaNFE> findNotaNFEById(Integer id);
     
     void deleteNotaNFEById(Integer id);
}
