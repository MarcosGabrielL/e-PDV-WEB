/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNfe;

import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Config;


/**
 *
 * @author Marcos
 */
public class StatusServicoSefaz {
    
    public static void main(String[] args) {

        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesNfe config = IniciaConfiguraçãoNFE.iniciaConfigurações();

            //Efetua Consulta
            TRetConsStatServ retorno = Nfe.statusServico(config, DocumentoEnum.NFE);

            //Resultado
            System.out.println();
            System.out.println("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());

        } catch (NfeException e) {
            System.err.println(e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(StatusServicoSefaz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
