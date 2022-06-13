/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNFCe;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import gerenciador.AcoesNfe.*;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TRetInutNFe;
import br.com.swconsultoria.nfe.util.InutilizacaoUtil;
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
public class InutilizacaoNFCe {
    public static String id;
    public static String motivo;
    
    public void recebechave(String id1, String motivo1){
    
    id = id1;
    motivo = motivo1;
        try {
            inutiliza();
        } catch (JAXBException ex) {
            Logger.getLogger(InutilizacaoNFCe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InutilizacaoNFCe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void inutiliza() throws JAXBException, CertificadoException, Exception {

        StringBuilder sb = new StringBuilder();
        
        try {

            
             ConfiguracoesNfe config = IniciaConfiguraçãoNFCE.iniciaConfigurações();  

            
            
            //Informe o CNPJ do emitente
            String cnpj = "XXX";
            //Informe a serie
            int serie = 1;
            //Informe a numeracao Inicial
            int numeroInicial = 1;
            //Informe a numeracao Final
            int numeroFinal = 50;
            //Informe a Justificativa da Inutilizacao
            String justificativa = "Teste de Inutilização";
            //Informe a data do Cancelamento
            LocalDateTime dataCancelamento = LocalDateTime.now();

            //MOnta Inutilização
            TInutNFe inutNFe = InutilizacaoUtil.montaInutilizacao(DocumentoEnum.NFCE,cnpj,serie,numeroInicial,numeroFinal,justificativa,dataCancelamento,config);

            //Envia Inutilização
            TRetInutNFe retorno = Nfe.inutilizacao(config,inutNFe, DocumentoEnum.NFCE,true);

            //Valida o Retorno da Inutilização
            RetornoUtil.validaInutilizacao(retorno);

            //Resultado
            System.out.println();
            System.out.println("# Status: " + retorno.getInfInut().getCStat() + " - " + retorno.getInfInut().getXMotivo());
            System.out.println("# Protocolo: " + retorno.getInfInut().getNProt());
            sb.append("\n# Status: " + retorno.getInfInut().getCStat() + " - " + retorno.getInfInut().getXMotivo());
            sb.append("\n# Protocolo: " + retorno.getInfInut().getNProt());
            
            //Cria ProcEvento da Inutilização
            String proc = InutilizacaoUtil.criaProcInutilizacao(config,inutNFe, retorno);
            System.out.println();
            System.out.println("# ProcInutilizacao : " + proc);
            sb.append("\n# ProcInutilizacao : " + proc);
            
            // NotasFiscais.RetornoInutilização1.setText(sb.toString());
            
            
        } catch (CertificadoException | NfeException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro: "+e);
        } catch (IOException ex) {
            Logger.getLogger(InutilizacaoNFCe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    
}

