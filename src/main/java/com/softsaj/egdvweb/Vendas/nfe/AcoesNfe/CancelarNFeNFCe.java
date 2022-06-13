/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNfe;

import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.util.ConstantesUtil;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TProcEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEnvEvento;
import br.com.swconsultoria.nfe.util.CancelamentoUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import java.time.LocalDateTime;

import javax.xml.bind.JAXBException;

/**
 *
 * @author Marcos
 */
public class CancelarNFeNFCe {
    
    public static String chave;
            public static String protocolo;
                    public static String cnpj;
                            public static String motivo;
                            
    public void recebe(String chave1, String protocolo1, String cnpj1, String motivo1){
    
        chave = chave1;
        protocolo = protocolo1;
        cnpj = cnpj1;
        motivo = motivo1;
        
        recebe();
    
    
    }
    
    public static void recebe() {

        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesNfe config =  IniciaConfiguraçãoNFE.iniciaConfigurações();  

            
            //Agora o evento pode aceitar uma lista de cancelaemntos para envio em Lote.
            //Para isso Foi criado o Objeto Cancela
            Evento cancela = new Evento();
            //Informe a chave da Nota a ser Cancelada
            cancela.setChave(chave);
            //Informe o protocolo da Nota a ser Cancelada
            cancela.setProtocolo(protocolo);
            //Informe o CNPJ do emitente
            cancela.setCnpj(cnpj);
            //Informe o Motivo do Cancelamento
            cancela.setMotivo(motivo);
            //Informe a data do Cancelamento
            cancela.setDataEvento(LocalDateTime.now());

            //Monta o Evento de Cancelamento
            TEnvEvento enviEvento = CancelamentoUtil.montaCancelamento(cancela, config);

            //Envia o Evento de Cancelamento
            TRetEnvEvento retorno = Nfe.cancelarNfe(config, enviEvento, true, DocumentoEnum.NFE);

            //Valida o Retorno do Cancelamento
            RetornoUtil.validaCancelamento(retorno);

            //Resultado
            System.out.println();
            retorno.getRetEvento().forEach( resultado -> {
                System.out.println("# Chave: " + resultado.getInfEvento().getChNFe());
                System.out.println("# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
                System.out.println("# Protocolo: " + resultado.getInfEvento().getNProt());
             /* NotasFiscais.jTextArea3.setText("# Chave: " + resultado.getInfEvento().getChNFe()+"\n"
                        +"# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo()+"\n"
                        +"# Protocolo: " + resultado.getInfEvento().getNProt());*/
            });

            //Cria ProcEvento de Cacnelamento
            String proc = CancelamentoUtil.criaProcEventoCancelamento(config, enviEvento, retorno.getRetEvento().get(0));
            System.out.println();
            System.out.println("# ProcEvento : " + proc);
          //  NotasFiscais.jTextArea3.insert("\n"+"# ProcEvento : " + proc, NotasFiscais.jTextArea6.getCaretPosition());
            

            
            

        } catch (CertificadoException | NfeException | JAXBException e ) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println();
            System.err.println("# Erro: "+e.getMessage());
        }
    }
    
}
