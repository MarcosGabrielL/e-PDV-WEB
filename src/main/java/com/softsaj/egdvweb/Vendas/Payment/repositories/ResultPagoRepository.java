/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.repositories;

import com.softsaj.egdvweb.Vendas.Payment.models.ResultPago;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface ResultPagoRepository extends JpaRepository<ResultPago,Long> {

	ResultPago findResultPagoById(Long id);

	@Query("select n from ResultPago n where n.preferenceId = ?1")
	List<ResultPago> userResultPago(String userId);

	 void deleteResultPagoById(Long id);
}
