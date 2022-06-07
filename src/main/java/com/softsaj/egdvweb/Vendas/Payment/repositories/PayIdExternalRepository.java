/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.repositories;

import com.softsaj.egdvweb.Vendas.Payment.models.PayIdExternal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface PayIdExternalRepository extends JpaRepository<PayIdExternal,Long> {

	PayIdExternal findPayIdExternalById(Long id);

	@Query("select n from PayIdExternal n where n.type = ?1")
	List<PayIdExternal> userPayIdExternal(String userId);

	 void deletePayIdExternalById(Long id);
}
