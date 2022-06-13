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
import br.com.swconsultoria.nfe.dom.enuns.ManifestacaoEnum;
import br.com.swconsultoria.nfe.schema.envConfRecebto.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envConfRecebto.TRetEnvEvento;
import br.com.swconsultoria.nfe.util.ManifestacaoUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import com.softsaj.egdvweb.Vendas.models.Fiscal;
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Marcos
 */
public class ManifestaçãoNfe {
    
    public static String chave;
    public static String cnpj ;
    public static String tipo;
    
      @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
    
    public void recebechave(String chave1, String motivo1) throws CertificadoException{
    
        tipo = motivo1;
    chave = chave1;
    
   
    for(Fiscal f: fiscalservice.findAll()){
         cnpj = f.getCNPJ();
         }
    cnpj = cnpj.replaceAll("[^0-9]", "");
    
    manifesta();
    }
    
    public static void manifesta() {
         StringBuilder sb = new StringBuilder();
        
        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesNfe config =  IniciaConfiguraçãoNFE.iniciaConfigurações();  

            //Agora o evento pode aceitar uma lista de Manifestções para envio em Lote.
            //Para isso Foi criado o Objeto Manifestada
            Evento manifesta = new Evento();
            //Informe a chave da Nota a ser Manifestada
            manifesta.setChave(chave);
            //Informe o CNPJ do emitente
            manifesta.setCnpj(cnpj);
            //Caso o Tipo de manifestação seja OPERAÇÂO Não REALIZADA, Informe o Motivo do Manifestacao
            manifesta.setMotivo("Manifestacao");
            //Informe a data do Manifestacao
            manifesta.setDataEvento(LocalDateTime.now());
            //Informe o Tipo da Manifestação
            if(tipo.equals("Confirmação da Operação")){
                manifesta.setTipoManifestacao(ManifestacaoEnum.CONFIRMACAO_DA_OPERACAO);
            }
            if(tipo.equals("Desconhecimento da Operação")){
                manifesta.setTipoManifestacao(ManifestacaoEnum.DESCONHECIMENTO_DA_OPERACAO);
            }
            if(tipo.equals("Operação Não Realizada")){
                manifesta.setTipoManifestacao(ManifestacaoEnum.OPERACAO_NAO_REALIZADA);
            }
            if(tipo.equals("Ciência da Emissão")){
                manifesta.setTipoManifestacao(ManifestacaoEnum.CIENCIA_DA_OPERACAO);
            }
            

            //Monta o Evento de Manifestação
            TEnvEvento enviEvento = ManifestacaoUtil.montaManifestacao(manifesta, config);

            //Envia o Evento de Manifestação
            TRetEnvEvento retorno = Nfe.manifestacao(config, enviEvento, true);

            //Valida o Retorno do Cancelamento
            RetornoUtil.validaManifestacao(retorno);

            //Resultado
            System.out.println();
            retorno.getRetEvento().forEach( resultado -> {
                System.out.println("# Chave: " + resultado.getInfEvento().getChNFe());
                System.out.println("# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
                System.out.println("# Protocolo: " + resultado.getInfEvento().getNProt());
                sb.append("\n# Chave: " + resultado.getInfEvento().getChNFe()+ "\n# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
            });

            //Cria ProcEvento de Manifestacao
            String proc = ManifestacaoUtil.criaProcEventoManifestacao(config, enviEvento, retorno.getRetEvento().get(0));
            System.out.println();
            System.out.println("# ProcEvento : " + proc);
            sb.append("\n# ProcEvento : " + proc);
            //NotasFiscais.retornoManifestação.setText(sb.toString());
        } catch (Exception e) {
            System.err.println();
            System.err.println("# Erro: "+e.getMessage());
        }

    }
    
}
