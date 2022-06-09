/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.repositories;

import com.softsaj.egdvweb.models.Vendedor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
 
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
 
/**
 *
 * @author Marcos
 */
@SpringBootTest
public class VendedorRepoTest {
    
    @Autowired
    private VendedorRepository vendedorRepo;
    
    @Test
    void isvendedorExitsById() {
        Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("YYYY-MM-dd hh:mm:ssXXX",locale);
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date d = new Date();
                String data = formatador.format(d.getTime());
                
                
        Vendedor vendedor = new Vendedor();
        vendedor.setId(new Long(1));
        vendedor.setNomefantasia("Nome");
        vendedor.setRua("Rua dos Bobos");
        vendedor.setEmail("email@email.com");
        vendedor.setTelefone("+55 (75) 9 88525220");
        vendedor.setDatainicio(data);
        vendedor.setDatafim(data);
        vendedor.setAmbiente(0);
        vendedor.setSerie(0);
        vendedor.setDescricao("Olá, sou "+vendedor.getNomefantasia()+" : Decisões: Se você não consegue decidir, a resposta é não. Se dois caminhos igualmente difíceis, escolha o mais doloroso a curto prazo (evitar a dor é criar uma ilusão de igualdade).");
        
        vendedorRepo.save(vendedor);
        Boolean actualResult = vendedorRepo.isVendedorExitsById(vendedor.getId());
        assertThat(actualResult).isTrue();
    }
    
}
