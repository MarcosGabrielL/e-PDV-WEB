/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe;

import gerenciador.AcoesNfe.*;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;


/**
 *
 * @author Marcos
 */
public class StatusServicoSefazNFCE {
    
    public static void main(String[] args) {

        try {

           
           ConfiguracoesNfe config = IniciaConfiguraçãoNFCE.iniciaConfigurações();

            //Efetua Consulta
            TRetConsStatServ retorno = Nfe.statusServico(config, DocumentoEnum.NFE);

            //Resultado
            System.out.println();
            System.out.println("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
        } catch (Exception e) {
            System.err.println("# Erro: "+e.getMessage());
        }

    }
    
}
