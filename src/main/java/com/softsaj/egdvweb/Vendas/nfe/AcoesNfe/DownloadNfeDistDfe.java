/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.nfe.AcoesNfe;

import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.*;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt.LoteDistDFeInt.DocZip;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import com.softsaj.egdvweb.Vendas.models.Fiscal;
import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.models.RelatorioNFE;
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author Marcos
 */
public class DownloadNfeDistDfe {
    
    //Download Nfe Por NSU(Número Sequencial Único de NF-e.) e Chave
    
    public static String chave;
    public static String cnpj;
    
      @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
    
    public static void chave(String chave1){
    
        chave = chave1.replaceAll(" ", "");
        System.err.println("Chave: "+chave);
        distnfe();
    }
    
    public static void distnfe() {

        try {
            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesNfe config = IniciaConfiguraçãoNFE.iniciaConfigurações(); 

            RetDistDFeInt retorno;

            //Para Consulta Via CHAVE
         for(Fiscal f: fiscalservice.findAll()){
         cnpj = f.getCNPJ();
          
         }
       cnpj = cnpj.replaceAll("[^0-9]", "");
       
        retorno = Nfe.distribuicaoDfe(config,PessoaEnum.JURIDICA, cnpj, ConsultaDFeEnum.CHAVE, chave);
    

            System.out.println("Status:" + retorno.getCStat());
            System.out.println("Motivo:" + retorno.getXMotivo());
            System.out.println("NSU:" + retorno.getUltNSU());
            System.out.println("Max NSU:" + retorno.getMaxNSU());

            //DistribuiçãoNFE.jTextArea7.insert("Status:" + retorno.getCStat()+"\nMotivo:" + retorno.getXMotivo() + 
              //      "\nNSU:" + retorno.getUltNSU() + "\nMax NSU:" + retorno.getMaxNSU(), DistribuiçãoNFE.jTextArea7.getCaretPosition());
            
            if (StatusEnum.DOC_LOCALIZADO_PARA_DESTINATARIO.getCodigo().equals(retorno.getCStat())) {
                System.out.println();
                System.out.println("# Status: " + retorno.getCStat() + " - " + retorno.getXMotivo());
                System.out.println("# NSU Atual: " + retorno.getUltNSU());
                System.out.println("# Max NSU: " + retorno.getMaxNSU());
                System.out.println("# Max NSU: " + retorno.getMaxNSU());

                //Aqui Recebe a Lista De XML (No Maximo 50 por Consulta)
                List<DocZip> listaDoc = retorno.getLoteDistDFeInt().getDocZip();
                for (DocZip docZip : listaDoc) {
                    System.out.println();
                    System.out.println("# Schema: " + docZip.getSchema());
                    switch (docZip.getSchema()) {
                        case "resNFe_v1.01.xsd":
                            System.out.println("# Este é o XML em resumo, deve ser feito a Manifestação para o Objeter o XML Completo.");
                            JOptionPane.showMessageDialog(null, "# Este é o XML em resumo, deve ser feito a Manifestação para o Objeter o XML Completo.");
                            break;
                        case "procNFe_v4.00.xsd":
                            System.out.println("# XML Completo.");
                            break;
                        case "procEventoNFe_v1.00.xsd":
                            System.out.println("# XML Evento.");
                            break;
                    }
                    //Transforma o GZip em XML
                    String xml = XmlNfeUtil.gZipToXml(docZip.getValue());
                    System.out.println("# XML: " + xml);
                        if(docZip.getSchema().substring(0, 3).equals("res")){

                        }else{
                        SalvaXML(XmlNfeUtil.gZipToXml(docZip.getValue()));
                        }
                 /*   DistribuiçãoNFE.jTextArea7.insert("\nSchema: " + docZip.getSchema()+"\n"
                            + "NSU:" + docZip.getNSU()+"\n"
                            ,DistribuiçãoNFE.jTextArea7.getCaretPosition());*/
                }
                }
            

        } catch (Exception e) {
            System.err.println();
            System.err.println("# Erro: "+e.getMessage());
        }
            

    }

   
    
    
    public static void SalvaXML(final String xml){
    
        new Thread() {
                @Override
                public void run() {
                    
                  //  DistribuiçãoNFE.jTextArea7.insert("\n\nSalvando XML...Aguarde", DistribuiçãoNFE.jTextArea7.getCaretPosition());
        
        
            
            //Salvar XML na pasta urlxml
            //Com nome de Nota
            Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-YYYY_hh-mm-ss");
                Date d = new Date();
                String data = formatador.format(d.getTime());
            String Nota = "Chave-"+chave+"_Dia-"+data;
            String path  = System.getProperty("user.home") + "\\Desktop";
            String urlDir = path+"\\NotasEntradas\\XML";
            String urlxml = path+"\\NotasEntradas\\XML\\"+Nota+".xml";
                
                File diretorio = new File(urlDir);      
                if (!diretorio.exists()) {
                    diretorio.mkdirs();
            }      
         
        try {
            Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(urlxml),"UTF-8"));
            file.write(xml);      
            file.close(); 
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Salvar Nota:  "+ex);
        }
            
            
            
            
            //Ler o XML, pega as informações, e mostra na tabela
            System.err.println("diretorio: "+urlxml);
                    try {
                        CarregaXML(urlxml);
                    } catch (ParseException ex) {
                        Logger.getLogger(DownloadNfeDistDfe.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(DownloadNfeDistDfe.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
            
           
      
      }
        }.start();
    
    
    
    
    
    
    }
    
    public static void CarregaXML(String caminhoXML) throws ParseException, ParserConfigurationException{
    
         //DistribuiçãoNFE.jTextArea7.insert("\n\nCarregando Nota Fiscal de Entrada...Aguarde", DistribuiçãoNFE.jTextArea7.getCaretPosition());
    
          System.out.println("XML2RELATORIO");
        
        try{
            
             //Pega o XML completo da nota gerada
            File inputFile = new File(caminhoXML);
            //File inputFile = new File("C:\\Users\\Marcos\\Desktop\\Notas Fiscais\\XML\\a.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xml = builder.parse(inputFile);
            xml.getDocumentElement().normalize();
            
            //Cria o objeto que armazenara os dados pros fields
            Collection <RelatorioNFE>  campos = new ArrayList<RelatorioNFE>();
            RelatorioNFE dadosrelatorio = new RelatorioNFE();
             System.err.println("Aqui 1");
            
            // Dados do emitente vindos do xml
            NodeList emitList = xml.getElementsByTagName("emit");
            Node emitNode = emitList.item(0);
            Element emitElement = (Element) emitNode;
            //emitenteRazaoSocial
             try{
            dadosrelatorio.setEmi_Nome(emitElement.getElementsByTagName("xNome").item(0).getTextContent());
            }catch(Exception e){
                dadosrelatorio.setEmi_Nome("");
            }
            System.err.println("Emitente Razão Social: "+dadosrelatorio.getEmi_Nome());
            //emitenteCnpj
            try{
            dadosrelatorio.setEmi_CNPJ(emitElement.getElementsByTagName("CNPJ").item(0).getTextContent());
            }catch(Exception e){
                dadosrelatorio.setEmi_CNPJ("");
            }
            //System.err.println("Emitente CNPJ: "+dadosrelatorio.getEmi_CNPJ());
            //Nome Fantasia
            try{
            dadosrelatorio.setEmi_Fantasia(emitElement.getElementsByTagName("xFant").item(0).getTextContent()); 
            }catch(Exception e){
                dadosrelatorio.setEmi_Fantasia("");
            }
            //System.err.println("Emitente Fantasia: "+dadosrelatorio.getEmi_Fantasia());
            
            // Dados do Destinatário vindo do xml
             // Dados do Destinatário vindo do xml
            NodeList destList = xml.getElementsByTagName("dest");
            Node destNode = destList.item(0);
                Element destElement = (Element) destNode;
                try {
                    
                    //destinatarioCpf
                    dadosrelatorio.setDest_CNPJ(destElement.getElementsByTagName("CNPJ").item(0).getTextContent());
                    //dadosrelatorio.setDestinatarioEndereco(destElement.getElementsByTagName("xLgr").item(0).getTextContent());
                    //dadosrelatorio.setDestinatarioEnderecoNro(destElement.getElementsByTagName("nro").item(0).getTextContent());
                } catch (Exception e) {
                   
                }
                    //destinaraioRazaoSocial
                   // dadosrelatorio.setDest_Nome(destElement.getElementsByTagName("xNome").item(0).getTextContent());
             
            // Dados da nota vindos do XML
            // Informações de Totais
            NodeList totList = xml.getElementsByTagName("ICMSTot");
            Node totNode = totList.item(0);
            Element totElement = (Element) totNode;
            //valorTotalNota
            try{
            dadosrelatorio.setValorNF(totElement.getElementsByTagName("vNF").item(0).getTextContent());
            }catch(Exception e){
                dadosrelatorio.setValorNF("");
            }
            //ValorTotalProduto
            try{
            dadosrelatorio.setValorProdutos(totElement.getElementsByTagName("vProd").item(0).getTextContent());
            }catch(Exception e){
                
            }        
            // Numero, Serie, Emissao
            NodeList ideList = xml.getElementsByTagName("ide");
            Node ideNode = ideList.item(0);
            Element ideElement = (Element) ideNode;
            
            //nNF
            dadosrelatorio.setNumeroNF(ideElement.getElementsByTagName("nNF").item(0).getTextContent());          // Numero
            //serie
            dadosrelatorio.setSerie(ideElement.getElementsByTagName("serie").item(0).getTextContent());        // serie
            //dataEmissao
            String data = ideElement.getElementsByTagName("dhEmi").item(0).getTextContent(); 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");   
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = sdf.parse(data);
            dadosrelatorio.setDataEmissao(f.format(date));  // Data/hora
            dadosrelatorio.setData_Saida(f1.format(date));  // Data
            dadosrelatorio.setHora_Saida(f2.format(date));  // Data/hora
            //tpEmis
            dadosrelatorio.setTipoNF(ideElement.getElementsByTagName("tpEmis").item(0).getTextContent()); 
            //Ambiente
            dadosrelatorio.setAmbiente(ideElement.getElementsByTagName("tpAmb").item(0).getTextContent()); 
            
            // Chave, Protocolo, Data/Hora Autorização
            NodeList protList = xml.getElementsByTagName("infProt");
            Node protNode = protList.item(0);
            Element protElement = (Element) protNode;
            //**********************************ChaveAcesso
            try{
                dadosrelatorio.setChave(protElement.getElementsByTagName("chNFe").item(0).getTextContent());
                 //System.err.println("Chave: "+protElement.getElementsByTagName("chNFe").item(0).getTextContent());
            }catch(Exception e){
                dadosrelatorio.setChave("");
            }
            //**********************************dhRecbto
            try{
                String dhRecbto = protElement.getElementsByTagName("dhRecbto").item(0).getTextContent();
                Date data_recibo = sdf.parse(dhRecbto);
                dadosrelatorio.setDataHoraAutorizacao(f.format(data_recibo));
           }catch(Exception e){
                Date data_recibo = sdf.parse(data);
                dadosrelatorio.setDataHoraAutorizacao(f.format(data_recibo));
           }
            //**********************************nProt
            try{
                dadosrelatorio.setProtocolo(protElement.getElementsByTagName("nProt").item(0).getTextContent()); 
            }catch(Exception e){
            dadosrelatorio.setProtocolo("123456789101112"); // Protocolo
            }
            //versão
            dadosrelatorio.setVersao("4.00");
            //Troco
            //dadosrelatorio.setTroco(getTroco());
            //desconto
            //dadosrelatorio.setDesconto("0");
            //detCount
            //dadosrelatorio.setDetCount(getCount());
            
            
            
            //Dados de Pagamento vindo do XML
            NodeList pagList = xml.getElementsByTagName("pag");
            Node pagNode = pagList.item(0);
            Element pagElement = (Element) pagNode;
            
            String Forma = pagElement.getElementsByTagName("tPag").item(0).getTextContent();
            //System.out.println("FORMA DE PAGAMENTO: "+pagElement.getElementsByTagName("tPag").item(0).getTextContent());
            // Tributos aprox
            String TribAp = null;
            try {
                //informacoesComplementares2
            TribAp = "Tributos Incidentes (Lei Federal 12.741/2012) - Total R$ " +totElement.getElementsByTagName("vTotTrib").item(0).getTextContent();
            } catch (Exception e) {
               // System.out.println("Valor total de tributos: " + e);
            }
            
            
             //Lista com parametros do produtos para mandar para o dataset detalhes
            
            // Dados dos Itens vindo do xml
            NodeList itensList = xml.getElementsByTagName("det");
             List <Produto> itens = new ArrayList<>();
           // System.err.println("itensList.getLength(): "+itensList.getLength());
            for(int i = 0; i < itensList.getLength(); ){
                Node itensNode = itensList.item(i);
                Element itensElement = (Element) itensNode;
                Produto item = new Produto();
                //nItem
                //item.setnItem(itensElement.getAttribute("nItem"));
                 //codigo
                        item.setCEAN(itensElement.getElementsByTagName("cEAN").item(0).getTextContent());
                        //descricao
                        item.setDescricao(itensElement.getElementsByTagName("xProd").item(0).getTextContent());
                        //BC
                        //item.setBCICMS(itensElement.getElementsByTagName().item(0).getTextContent());
                        //Aliquota ICMS
                        //item.setProd_AliqICMS(p.getAliquotaICMS());
                        //Valor ICMS
                        //item.setProd_valorICMS(String.valueOf(Float.parseFloat(p.getICMS())*Float.parseFloat(itensElement.getElementsByTagName("qCom").item(0).getTextContent())));
                       //valorTotal
                        item.setSubTotal(Float.parseFloat(itensElement.getElementsByTagName("vProd").item(0).getTextContent()));
                        //unidadeTributaria
                        item.setUnidadeTributavel(itensElement.getElementsByTagName("uTrib").item(0).getTextContent());
                        //valorUnitarioComercial
                        item.setPrecoun(itensElement.getElementsByTagName("vUnCom").item(0).getTextContent());
                        //quantidadeComercial
                        item.setQuantidade(Float.parseFloat(itensElement.getElementsByTagName("qCom").item(0).getTextContent()));
                        //valorUnitarioTributario
                        item.setVUnTrib(itensElement.getElementsByTagName("vUnTrib").item(0).getTextContent());
                        //quantidadeTributaria
                        item.setQTrib(itensElement.getElementsByTagName("qTrib").item(0).getTextContent());
                        //unidadeComercial
                        item.setUnidade(itensElement.getElementsByTagName("uCom").item(0).getTextContent());
                        //NCM
                       // item.setNCM(itensElement.getElementsByTagName("NCM").item(0).getTextContent());
                        //CFOP
                        item.setCFOP(itensElement.getElementsByTagName("CFOP").item(0).getTextContent());
                        //CST
                        try{
                        item.setCST(itensElement.getElementsByTagName("CST").item(0).getTextContent());
                        }catch(Exception e){
                        //CSOSN
                        item.setCST(itensElement.getElementsByTagName("CSOSN").item(0).getTextContent());
                        }
                        //AliqIPI
                        //item.set("0");
                        //ValorIPI
                        item.setVPIS("0");

                        i++;
                        itens.add(item);
            }
            
            carregartabela(itens,dadosrelatorio);
            
          
               
               
        } catch (SAXException ex) {
               
            System.out.println("Erro ao Ler XML:  "+ex);
            Logger.getLogger(DownloadNfeDistDfe.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
        } catch (IOException ex) {
             //   JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
            System.out.println("Erro ao Ler XML:  "+ex);
           Logger.getLogger(DownloadNfeDistDfe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
           //JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
            System.out.println("Erro ao Ler XML:  "+ex);
           Logger.getLogger(DownloadNfeDistDfe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
          // JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
            System.out.println("Erro ao Ler XML:  "+ex);
           Logger.getLogger(DownloadNfeDistDfe.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    
    
    }
    
    public static void carregartabela(List<Produto> itens,RelatorioNFE dadosrelatorio){
        
       
        
         
        
        
    }
}
