/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe;

import gerenciador.AcoesNfe.*;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.schema.envcce.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envcce.TRetEnvEvento;
import br.com.swconsultoria.nfe.util.CartaCorrecaoUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.xml.bind.JAXBException;
/**
 *
 * @author Marcos
 */
public class CartaCorrecaoNFCe {
    
    public static String chave;
    public static String cnpj;
    public static String sequencia;
    public static String motivo;
    
    
    public void recebe(String chave1, String cnpj1, String sequencia1, String motivo1){
    
        chave = chave1;
             cnpj = cnpj1;
             sequencia = sequencia1;
             motivo = motivo1;
    
    mandacarta();
    
    }
    
    public static void mandacarta() {
        
        StringBuilder sb = new StringBuilder();

        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesNfe config = IniciaConfiguraçãoNFCE.iniciaConfigurações();  

            
            //Agora o evento pode aceitar uma lista de cancelaemntos para envio em Lote.
            //Para isso Foi criado o Objeto Cancela
            Evento cce = new Evento();
            //Informe a chave da Nota a ser feita a CArta de Correção
            cce.setChave(chave);
            //Informe o CNPJ do emitente
            cce.setCnpj(cnpj);
            //Informe o Texto da Carta de Correção
            cce.setMotivo(motivo);
            //Informe a data da Carta de Correção
            cce.setDataEvento(LocalDateTime.now());
            //Informe a sequencia do Evento
            cce.setSequencia(1);

            // Monta o Evento
            TEnvEvento envEvento = CartaCorrecaoUtil.montaCCe(cce,config);

            //Envia a CCe
            TRetEnvEvento retorno = Nfe.cce(config, envEvento, true);

            //Valida o Retorno do Carta de Correção
            RetornoUtil.validaCartaCorrecao(retorno);

            //Resultado
            System.out.println();
            retorno.getRetEvento().forEach( resultado -> {
                System.out.println("# Chave: " + resultado.getInfEvento().getChNFe());
                System.out.println("# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
                System.out.println("# Protocolo: " + resultado.getInfEvento().getNProt());
                
                sb.append("# Chave: " + resultado.getInfEvento().getChNFe());
            sb.append("# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
            sb.append("# Protocolo: " + resultado.getInfEvento().getNProt());
            });
            

            //Cria ProcEvento da CCe
            String proc = CartaCorrecaoUtil.criaProcEventoCCe(config, envEvento, retorno);
            System.out.println();
            System.out.println("# ProcEvento : " + proc);
            
            sb.append("# ProcEvento : " + proc);
          //  NotasFiscais.jTextPane4.setText(sb.toString());
            
             } catch (Exception e) {
            System.err.println();
            System.err.println(e.getMessage());
        }

    }
    
}
