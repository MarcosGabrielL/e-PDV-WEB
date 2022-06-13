/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.Vendas.repositories;

import com.softsaj.egdvweb.Vendas.models.NotaNFCe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface NotaNFCeRepository extends JpaRepository<NotaNFCe, Long> {
    
     Optional<NotaNFCe> findNotaNFCeById(Integer id);
     
     void deleteNotaNFCeById(Integer id);
}
