/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.Relatorios;


import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.ServletContext;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import com.softsaj.egdvweb.Vendas.models.Adcionais;
import com.softsaj.egdvweb.Vendas.models.Caixa;
import com.softsaj.egdvweb.Vendas.models.DDConsumidor;
import com.softsaj.egdvweb.Vendas.models.Fiscal;
import com.softsaj.egdvweb.Vendas.models.PagImpress;
import com.softsaj.egdvweb.Vendas.models.ProdImpress;
import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.models.Relatorio;
import com.softsaj.egdvweb.Vendas.models.RelatorioNFE;
import com.softsaj.egdvweb.Vendas.models.Transportadora;
import com.softsaj.egdvweb.Vendas.services.FiscalService;
import com.softsaj.egdvweb.Vendas.services.NotaNFCeService;
import com.softsaj.egdvweb.Vendas.services.ProdutoService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
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
public class DanfeNFE {
    
    public static String troco;
    public static String count,nome,Logo,numero;
    private static boolean imprimiu = false;
    private static String NomeImpressora;
    private static HashMap map = new HashMap();
    private static DDConsumidor consumidor;
    private static Transportadora Trans;
    private static int Colunas;
    
     @Autowired
   private static FiscalService fiscalservice;
   @Autowired
   private static NotaNFCeService notaNFCeService;
   
   @Autowired
   private static ProdutoService ProdutoService;
    
    public static void recebedados(String Impressora, String vtroco, String qitem, String urlxml,String Code,String Nome, DDConsumidor a,Transportadora transportadora, String image){
    
        System.out.println("RECEBE DADOS");
        Trans = transportadora;
        NomeImpressora = Impressora;
        consumidor = a;
        Logo = image;
        String QrCode = null;
        String XML = "C:\\Users\\Marcos\\Desktop\\Notas Fiscais\\XML\\a.xml";
        if(Code==null){
         Code = "99999999999999999999999999999999999999999999";
        }else{
        
            if(Code.equals("")){
                 Code = "99999999999999999999999999999999999999999999";
            }else{
                 QrCode = Code;
            }
        }
        
        if(urlxml==null){
            XML = "C:\\Users\\Marcos\\Desktop\\Notas Fiscais\\XML_NFE\\a.xml";
        }else{
            if(urlxml.equals("")){
                XML = "C:\\Users\\Marcos\\Desktop\\Notas Fiscais\\XML_NFE\\a.xml";
            }else{
                XML = urlxml;
            }
        }
        
      if(vtroco == null){
          setTroco("0");
      }else{
          if(vtroco.equals("")){
              setTroco("0");
          }else{
              setTroco(vtroco);
          }
      }
      
      if(qitem == null){
          setCount("1");
      }else{
          if(qitem.equals("")){
              setCount("1");
          }else{
              setCount(qitem);
          }
      }
      
      XML2Relatorio(XML,QrCode);
      System.out.println("XML: "+XML);
      System.out.println("QRCode: "+QrCode);
    }
     
   //procura impressora se não achar imprime na padrão
    private static void ImpressoraSelecionada(String printerName,JasperPrint jasperPrint) throws JRException{     
        
                 
            //Lista todas impressoras até encontrar a enviada por parametro  
            PrintService serviceFound = null;  
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null,null);  
            
            for(PrintService service:services){  
                            if(service.getName().trim().equals(printerName.trim()))  
                     serviceFound = service;  
                                  
            }  
              
            if (serviceFound == null){  
                try{
                    // se nao achar impressora configurada imprima na impressora padrao  
                        JasperPrintManager.printReport(jasperPrint, false);  
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Impressora não encontrada! Impressão enviada para impressora padrao");
                }    
                        }else{   
           /* JRPrintServiceExporter exporter;
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
           // printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
            printRequestAttributeSet.add(new Copies(1));

            // these are deprecated
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, serviceFound);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, serviceFound.getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();*/

                       
           /* JRExporter exporter1 = new JRPrintServiceExporter();  
                        //JRExporter exporter = new JRPdfExporter();  
            
                        exporter1.setParameter(JRPrintServiceExporterParameter.JASPER_PRINT, jasperPrint);  
                    exporter1.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,serviceFound.getAttributes());  
                exporter1.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);  
            exporter1.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);  
            exporter1.exportReport();  
             */
            }
            
            if(imprimiu){
              //  EmitirNFE.Tramite("CAIXA","LIVRE!!");
            }
        
    }  
    
    
    // Cria o Danfe com os dados do xml
    public static void gerarDanfe( JRDataSource jr){
        try {
            System.out.println("GERAR DANFE");
            //JRBeanCollectionDataSource itens = new JRBeanCollectionDataSource(detalhes);
            //JRBeanCollectionDataSource ModoPagamentos = new JRBeanCollectionDataSource(pagamentos);
            
            //Local+Nome arquivopdf(Nota)
            String NumeroData = consumidor.getNome()+"--"+numero;
            String path  = System.getProperty("user.home") + "\\Desktop";
            String output = path+"\\Notas Fiscais\\PDF_NFE\\"+ NumeroData+".pdf"; 
            String jrurl = new File(".\\resources\\DanfeR.jrxml").getAbsolutePath();
            String jasper = new File(".\\resources\\DanfeR.jasper").getAbsolutePath();
           
            //InputStream image = this.getClass().getResourceAsStream("imagens/fundo.png");
            map.put("Logo", Logo); 
            map.put("COLUMN_COUNT", Colunas);
           //map.put("SubDataSourcePagamentos", ModoPagamentos);
            // Relatório compilado
            JasperReport report = null;
           try{
           // report = (JasperReport) JRLoader.loadBytes(jasper.getBytes());
            }catch(Exception e){
                System.err.println("Erro ao ler jasper");
            }
           
            // Relatório nao compilado
            //JasperReport report = JasperCompileManager.compileReport(jrurl);
            
            JasperPrint print = JasperFillManager.fillReport(report, map, jr);
            JasperViewer jrviewer = new JasperViewer(print, true);
            //jrviewer.setVisible(true);
            
            //Salva para pdf
            JasperExportManager.exportReportToPdfFile(print, output);
            System.err.println(NomeImpressora);
            ImprimirSelecionada(NomeImpressora,print);
             
           imprimiu = true;
        } catch (JRException e) {
            System.err.println("Erro ao imprimir Nota:  "+e);
            JOptionPane.showMessageDialog(null, "Erro ao imprimir Nota:  "+e);
        }
        
        if(imprimiu){
                // Tela_Caixa.Info.setText("Caixa Livre");
            }
    }
    
    public static void XML2Relatorio(String xml_Nfce, String Qr){
        
        System.out.println("XML2RELATORIO");
        
        
        try{
            
             //Pega o XML completo da nota gerada
            File inputFile = new File(xml_Nfce);
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
            dadosrelatorio.setEmi_Nome(emitElement.getElementsByTagName("xNome").item(0).getTextContent());
            System.err.println("Emitente Razão Social: "+dadosrelatorio.getEmi_Nome());
            //emitenteCnpj
            dadosrelatorio.setEmi_CNPJ(emitElement.getElementsByTagName("CNPJ").item(0).getTextContent());
            System.err.println("Emitente CNPJ: "+dadosrelatorio.getEmi_CNPJ());
            //Nome Fantasia
            dadosrelatorio.setEmi_Fantasia(emitElement.getElementsByTagName("xFant").item(0).getTextContent()); 
            System.err.println("Emitente Fantasia: "+dadosrelatorio.getEmi_Fantasia());
            //emitenteEndereco
            dadosrelatorio.setEmi_Logradouro(emitElement.getElementsByTagName("xLgr").item(0).getTextContent());
            System.err.println("Emitente Logradouro: "+dadosrelatorio.getEmi_Logradouro());
            //emitenteenderecoNro
            dadosrelatorio.setEmi_Numero(emitElement.getElementsByTagName("nro").item(0).getTextContent());
            System.err.println("Emitente Numero: "+dadosrelatorio.getEmi_Numero());
            //emitentebairro
            dadosrelatorio.setEmi_Bairro(emitElement.getElementsByTagName("xBairro").item(0).getTextContent());
            System.err.println("Emitente Bairro: "+dadosrelatorio.getEmi_Bairro());
            //emitenteMunicipio
            dadosrelatorio.setEmi_Municipio(emitElement.getElementsByTagName("xMun").item(0).getTextContent());
            System.err.println("Emitente Municipio: "+dadosrelatorio.getEmi_Municipio());
            //emitenteUf
            dadosrelatorio.setEmi_UF(emitElement.getElementsByTagName("UF").item(0).getTextContent());
            System.err.println("Emitente Uf: "+dadosrelatorio.getEmi_UF());
            //emitenteCep
            dadosrelatorio.setEmi_CEP(emitElement.getElementsByTagName("CEP").item(0).getTextContent());
            System.err.println("Emitente CEP: "+dadosrelatorio.getEmi_CEP());
            //EmitenteTelefone
            try{
            dadosrelatorio.setEmi_Telefone(emitElement.getElementsByTagName("fone").item(0).getTextContent());
            System.err.println("Emitente Telefone: "+dadosrelatorio.getEmi_Telefone());
            }catch(Exception e){
              //  FiscalDAO fdao = new FiscalDAO();
            String c;
            for(Fiscal f:fiscalservice.findAll()){
                
                dadosrelatorio.setEmi_Telefone(f.getTelefone());
           
           
            }
            }
            //Insricção Estadual
            dadosrelatorio.setEmi_IE(emitElement.getElementsByTagName("IE").item(0).getTextContent());
            System.err.println("Emitente Inscrição Estadual: "+dadosrelatorio.getEmi_IE());
            //IEST - IE
            //dadosrelatorio.setEmi_IEST(emitElement.getElementsByTagName("IE").item(0).getTextContent());
            //IM - Inscrição Municipal
            //dadosrelatorio.setEmi_IM(emitElement.getElementsByTagName("IM").item(0).getTextContent());
            
            // Dados do Destinatário vindo do xml
            NodeList destList = xml.getElementsByTagName("dest");
            Node destNode = destList.item(0);
            Element destElement = (Element) destNode;
                
                    if(consumidor.getCNPJ() == null || consumidor.getCNPJ().equals("")){
                        if(consumidor.getCPF() == null || consumidor.getCPF().equals("")){
                            dadosrelatorio.setDest_CNPJ("CNPJ/CPF não Informado");
                        }else{
                            dadosrelatorio.setDest_CNPJ(consumidor.getCEP());}
                    }else{
                        dadosrelatorio.setDest_CNPJ(consumidor.getCNPJ());}
                
                    //Destinatario Nome
                    System.err.println("Dest Nome: "+consumidor.getNome());
                    if(consumidor.getNome() == null || consumidor.getNome().equals("")){
                    dadosrelatorio.setDest_Nome("Nome não Informado"); 
                    }else{
                        dadosrelatorio.setDest_Nome(consumidor.getNome());}
                    //Destinatario IE
                    System.err.println("Dest IE: "+consumidor.getIE());
                    if(consumidor.getIE() == null ||consumidor.getIE().equals("") ){
                    dadosrelatorio.setDest_IE("IE não Informado");
                    }else{
                        dadosrelatorio.setDest_IE(consumidor.getIE());}
                    //Destinatario Rua
                    System.err.println("Dest Rua: "+consumidor.getRua());
                    if(consumidor.getRua()== null || consumidor.getRua().equals("")){
                    dadosrelatorio.setDest_Logradouro("Endereço não Informado");
                    }else{
                        dadosrelatorio.setDest_Logradouro(consumidor.getRua());}
                    //Destinatario Numero
                    System.err.println("Dest Numero: "+consumidor.getNumero());
                    if(consumidor.getNumero() == null || consumidor.getNumero().equals("")){
                    dadosrelatorio.setDest_Numero("Numero não Informado");
                    }else{
                        dadosrelatorio.setDest_Numero(consumidor.getNumero());}
                    //Destinatario Complemento
                    System.err.println("Dest Complemento: "+consumidor.getComplemento());
                    if(consumidor.getComplemento() == null || consumidor.getComplemento().equals("")){
                    dadosrelatorio.setDest_Complemento("Complemento não Informado");
                    }else{
                        dadosrelatorio.setDest_Complemento(consumidor.getComplemento());}
                    //Destinatario Bairro
                    System.err.println("Dest Bairro: "+consumidor.getBairro());
                    if(consumidor.getBairro() == null || consumidor.getBairro().equals("")){
                     dadosrelatorio.setDest_Bairro("Bairro não Informado");   
                    }else{
                        dadosrelatorio.setDest_Bairro(consumidor.getBairro());}
                    //Destinatario Cidade
                    System.err.println("Dest Cidade: "+consumidor.getCidade());
                    if(consumidor.getCidade()== null || consumidor.getBairro().equals("")){
                    dadosrelatorio.setDest_Municipio("Municipio não Informado");
                    }else{
                        dadosrelatorio.setDest_Municipio(consumidor.getCidade());}
                    //Destinatario UF
                    System.err.println("Dest UF: "+consumidor.getUF());
                    if(consumidor.getUF() == null || consumidor.getUF().equals("")){
                    dadosrelatorio.setDest_UF("UF não informado");
                    }else{
                        dadosrelatorio.setDest_UF(consumidor.getUF());
                    }
                    //Destinatario CEP
                    System.err.println("Dest CEP: "+consumidor.getCEP());
                    if(consumidor.getCEP() == null || consumidor.getCEP().equals("")){
                    dadosrelatorio.setDest_CEP("CEP não Informado");
                    }else{
                        dadosrelatorio.setDest_CEP(consumidor.getCEP());}
                    //Destinatario Telefone
                    System.err.println("Dest TEL: "+consumidor.getTelefone1());
                    if(consumidor.getTelefone1() == null || consumidor.getTelefone1().equals("")){
                    dadosrelatorio.setDest_Telefone("Telefone não informado");
                    }else{
                        dadosrelatorio.setDest_Telefone(consumidor.getTelefone1());}
                 
            
            //Dados Transporte
            dadosrelatorio.setFrete(Trans.getFrete());
            dadosrelatorio.setTrans_ANTT(Trans.getANTT());
            dadosrelatorio.setTrans_CNPJ(Trans.getCNPJ());
            dadosrelatorio.setTrans_Endereco(Trans.getEndereco());
            dadosrelatorio.setTrans_Especie(Trans.getEspecie());
            dadosrelatorio.setTrans_IE(Trans.getIE());
            dadosrelatorio.setTrans_Marca(Trans.getMarca());
            dadosrelatorio.setTrans_Municipio(Trans.getMunicipio());
            dadosrelatorio.setTrans_Nome(Trans.getNome());
            dadosrelatorio.setTrans_Numeracao(Trans.getNumeracao());
            dadosrelatorio.setTrans_PesoB(Trans.getPesoB());
            dadosrelatorio.setTrans_PesoL(Trans.getPesoL());
            dadosrelatorio.setTrans_Placa(Trans.getPlaca());
            
            
            
            // Dados da nota vindos do XML
            // Informações de Totais
            NodeList totList = xml.getElementsByTagName("ICMSTot");
            Node totNode = totList.item(0);
            Element totElement = (Element) totNode;
            //valorTotalNota
            dadosrelatorio.setValorNF(totElement.getElementsByTagName("vNF").item(0).getTextContent());
            //ValorTotalProduto
            dadosrelatorio.setValorProdutos(totElement.getElementsByTagName("vProd").item(0).getTextContent());
            //ValorBaseICMS
            dadosrelatorio.setValorBase(totElement.getElementsByTagName("vBC").item(0).getTextContent());
            //ValorICMS
            dadosrelatorio.setValorICMS(totElement.getElementsByTagName("vICMS").item(0).getTextContent());
            //Valor Base substituição Tributaria
            dadosrelatorio.setValorBaseST(totElement.getElementsByTagName("vBCST").item(0).getTextContent());        
            //Valor Substituição Tributariia
            dadosrelatorio.setValorST(totElement.getElementsByTagName("vST").item(0).getTextContent()); 
            //Valor Frete
            dadosrelatorio.setValorFrete(totElement.getElementsByTagName("vFrete").item(0).getTextContent());
            //Valor Seguro
            dadosrelatorio.setValorSeguro(totElement.getElementsByTagName("vSeg").item(0).getTextContent());
            //Valor Desconto
            dadosrelatorio.setValorDesconto(totElement.getElementsByTagName("vDesc").item(0).getTextContent());         
            //Valor Outro
            dadosrelatorio.setValorOutro(totElement.getElementsByTagName("vOutro").item(0).getTextContent());
            //ValorIPI
            dadosrelatorio.setValorIPI(totElement.getElementsByTagName("vIPI").item(0).getTextContent());
            //Valor Total Tributo
            dadosrelatorio.setValorTotalTrib(totElement.getElementsByTagName("vTotTrib").item(0).getTextContent());         
                            
            // Numero, Serie, Emissao
            NodeList ideList = xml.getElementsByTagName("ide");
            Node ideNode = ideList.item(0);
            Element ideElement = (Element) ideNode;
            //Natureza Operação
            dadosrelatorio.setNaturezaOperacao(ideElement.getElementsByTagName("natOp").item(0).getTextContent()); 
            //System.out.println("NATOP: "+ideElement.getElementsByTagName("natOp").item(0).getTextContent());
            //nNF
            dadosrelatorio.setNumeroNF(ideElement.getElementsByTagName("nNF").item(0).getTextContent());          // Numero
            numero = dadosrelatorio.getNumeroNF();
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
                System.err.println("Chave: "+protElement.getElementsByTagName("chNFe").item(0).getTextContent());
                dadosrelatorio.setChave("NFe"+protElement.getElementsByTagName("chNFe").item(0).getTextContent());
            }catch(Exception e){
                dadosrelatorio.setChave("Nfe29181011951317000120650010000000581633262341");
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
            System.out.println("FORMA DE PAGAMENTO: "+pagElement.getElementsByTagName("tPag").item(0).getTextContent());
            // Tributos aprox
            String TribAp = null;
            try {
                //informacoesComplementares2
            TribAp = "Tributos Incidentes (Lei Federal 12.741/2012) - Total R$ " +totElement.getElementsByTagName("vTotTrib").item(0).getTextContent();
            } catch (Exception e) {
                System.out.println("Valor total de tributos: " + e);
            }
            
            
             //Lista com parametros do produtos para mandar para o dataset detalhes
            List <RelatorioNFE> itens = new ArrayList<>();
            // Dados dos Itens vindo do xml
            NodeList itensList = xml.getElementsByTagName("det");
            System.err.println("itensList.getLength(): "+itensList.getLength());
            Colunas = itensList.getLength();
            for(int i = 0; i < itensList.getLength(); ){
                Node itensNode = itensList.item(i);
                Element itensElement = (Element) itensNode;
                RelatorioNFE item = new RelatorioNFE();
                //nItem
                //item.setnItem(itensElement.getAttribute("nItem"));
                if(i==0){
                         //codigo
                        dadosrelatorio.setProd_Codigo(itensElement.getElementsByTagName("cProd").item(0).getTextContent());
                        //descricao
                        dadosrelatorio.setProd_Descricao(itensElement.getElementsByTagName("xProd").item(0).getTextContent());
                        System.out.println("Produto: "+dadosrelatorio.getProd_Descricao());
                        String Cproduto = dadosrelatorio.getProd_Descricao();
                       
                      Produto p = ProdutoService.findProdutoById(new Long(Cproduto));
                            //BC
                            dadosrelatorio.setProd_BC(p.getBaseICMS());
                            System.err.println("BAse ICMS 1: "+dadosrelatorio.getProd_BC());
                            //Aliquota ICMS
                            dadosrelatorio.setProd_AliqICMS(p.getAliquotaICMS());
                            System.err.println("Aliquota ICMS 1: "+dadosrelatorio.getProd_AliqICMS());
                            //Valor ICMS
                            dadosrelatorio.setProd_valorICMS(String.valueOf(Float.parseFloat(p.getICMS())*Float.parseFloat(itensElement.getElementsByTagName("qCom").item(0).getTextContent())));
                        
                        //valorTotal
                        dadosrelatorio.setProd_Total(itensElement.getElementsByTagName("vProd").item(0).getTextContent());
                        //unidadeTributaria
                        //item.setUnidadeTributaria(itensElement.getElementsByTagName("uTrib").item(0).getTextContent());
                        //valorUnitarioComercial
                        dadosrelatorio.setProd_Valor(itensElement.getElementsByTagName("vUnCom").item(0).getTextContent());
                        //quantidadeComercial
                        dadosrelatorio.setProd_Quantidade(itensElement.getElementsByTagName("qCom").item(0).getTextContent());
                        //valorUnitarioTributario
                        //item.setValorUnitarioTributario(itensElement.getElementsByTagName("vUnTrib").item(0).getTextContent());
                        //quantidadeTributaria
                        //item.setQuantidadeTributaria(itensElement.getElementsByTagName("qTrib").item(0).getTextContent());
                        //unidadeComercial
                        dadosrelatorio.setProd_Unidade(itensElement.getElementsByTagName("uCom").item(0).getTextContent());
                        //NCM
                        dadosrelatorio.setProd_NCM(itensElement.getElementsByTagName("NCM").item(0).getTextContent());
                        //CFOP
                        dadosrelatorio.setProd_CFOP(itensElement.getElementsByTagName("CFOP").item(0).getTextContent());
                        //CST
                        dadosrelatorio.setProd_CST(itensElement.getElementsByTagName("CST").item(0).getTextContent());
                        //CSOSN
                        dadosrelatorio.setProd_CSOSN(itensElement.getElementsByTagName("CSOSN").item(0).getTextContent());
                        //AliqIPI
                        dadosrelatorio.setProd_AliqIPI("0");
                        //ValorIPI
                        dadosrelatorio.setProd_ValorIPI("0");

                        i++;
                        if(!(i < itensList.getLength())){
                            item.setFormaPagamento(Forma);
                            item.setInformacoes2(TribAp);
                        }
                        campos.add(dadosrelatorio);
                }else{
                        //codigo
                        item.setProd_Codigo(itensElement.getElementsByTagName("cProd").item(0).getTextContent());
                        //descricao
                        item.setProd_Descricao(itensElement.getElementsByTagName("xProd").item(0).getTextContent());
                        System.out.println("Produto: "+item.getProd_Descricao());
                        String Cproduto = item.getProd_Descricao();
                       
                       Produto p = ProdutoService.findProdutoById(new Long(Cproduto));
                            //BC
                            item.setProd_BC(p.getBaseICMS());
                            System.err.println("BAse ICMS: "+item.getProd_BC());
                            //Aliquota ICMS
                            item.setProd_AliqICMS(p.getAliquotaICMS());
                            System.err.println("Aliquota ICMS: "+item.getProd_AliqICMS());
                            //Valor ICMS
                            item.setProd_valorICMS(String.valueOf(Float.parseFloat(p.getICMS())*Float.parseFloat(itensElement.getElementsByTagName("qCom").item(0).getTextContent())));
                        
                        //valorTotal
                        item.setProd_Total(itensElement.getElementsByTagName("vProd").item(0).getTextContent());
                        //unidadeTributaria
                        //item.setUnidadeTributaria(itensElement.getElementsByTagName("uTrib").item(0).getTextContent());
                        //valorUnitarioComercial
                        item.setProd_Valor(itensElement.getElementsByTagName("vUnCom").item(0).getTextContent());
                        //quantidadeComercial
                        item.setProd_Quantidade(itensElement.getElementsByTagName("qCom").item(0).getTextContent());
                        //valorUnitarioTributario
                        //item.setValorUnitarioTributario(itensElement.getElementsByTagName("vUnTrib").item(0).getTextContent());
                        //quantidadeTributaria
                        //item.setQuantidadeTributaria(itensElement.getElementsByTagName("qTrib").item(0).getTextContent());
                        //unidadeComercial
                        item.setProd_Unidade(itensElement.getElementsByTagName("uCom").item(0).getTextContent());
                        //NCM
                        item.setProd_NCM(itensElement.getElementsByTagName("NCM").item(0).getTextContent());
                        //CFOP
                        item.setProd_CFOP(itensElement.getElementsByTagName("CFOP").item(0).getTextContent());
                        //CST
                        item.setProd_CST(itensElement.getElementsByTagName("CST").item(0).getTextContent());
                        //CSOSN
                        item.setProd_CSOSN(itensElement.getElementsByTagName("CSOSN").item(0).getTextContent());
                        //AliqIPI
                        item.setProd_AliqIPI("0");
                        //ValorIPI
                        item.setProd_ValorIPI("0");

                        i++;
                        if(!(i < itensList.getLength())){
                            item.setFormaPagamento(Forma);
                            item.setInformacoes2(TribAp);
                        }
                        campos.add(item);
                        itens.add(item);
                }
            }
            
            
            
           
            
            JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(campos);
            
               gerarDanfe(jr); 
               
               
        } catch (SAXException ex) {
               
            System.out.println("Erro ao Ler XML:  "+ex);
           // EmitirNFE.Tramite("Erro ao Ler XML:",""+ex.getMessage());
            //JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
        } catch (IOException ex) {
             //   JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
            System.out.println("Erro ao Ler XML:  "+ex);
           // EmitirNFE.Tramite("Erro ao Ler XML:",""+ex.getMessage());
        } catch (ParseException ex) {
           //JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
            System.out.println("Erro ao Ler XML:  "+ex);
           // EmitirNFE.Tramite("Erro ao Ler XML:",""+ex.getMessage());
        } catch (ParserConfigurationException ex) {
          // JOptionPane.showMessageDialog(null, "Erro ao Ler XML:  "+ex);
            System.out.println("Erro ao Ler XML:  "+ex);
           // EmitirNFE.Tramite("Erro ao Ler XML:",""+ex.getMessage());
        }
            
    }

    public static String getTroco() {
        return troco;
    }

    public static void setTroco(String troco) {
      //  DanfeNFCe.troco = troco;
    }

    public static String getCount() {
        return count;
    }

    public static void setCount(String count) {
      //  DanfeNFCe.count = count;
    }
    
    public static void ImprimirSelecionada(String printerName,JasperPrint jasperPrint) throws JRException{
    
    //Pega os nomes de todas as impressoras
    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

    //Pega impressora selecionada
    String selectedPrinter = printerName; 

    System.out.println("Number of print services: " + services.length);
    PrintService selectedService = null;

    //Definir as configurações de impressão
    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
    printRequestAttributeSet.add(MediaSizeName.ISO_A4);
    printRequestAttributeSet.add(new Copies(1));
    if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) { 
      printRequestAttributeSet.add(OrientationRequested.LANDSCAPE); 
    } else { 
      printRequestAttributeSet.add(OrientationRequested.PORTRAIT); 
    } 
    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
    printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

    /*JRPrintServiceExporter exporter = new JRPrintServiceExporter();
    SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
    configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
    configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
    configuration.setDisplayPageDialog(false);
    configuration.setDisplayPrintDialog(false);

    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    exporter.setConfiguration(configuration);

    //Itere através da impressora disponível, e uma vez combinado com o nosso <selectedPrinter>, vá em frente e imprima!
    if(services != null && services.length != 0){
      for(PrintService service : services){
          String existingPrinter = service.getName();
          if(existingPrinter.equals(selectedPrinter))
          {
              selectedService = service;
              break;
          }
      }
    }
    if(selectedService != null)
    {   
      try{
          //Lets the printer do its magic!
          exporter.exportReport();
      }catch(Exception e){
    System.out.println("JasperReport Error: "+e.getMessage());
      }
    }else{
        ImpressoraSelecionada("c",jasperPrint);
      System.out.println("JasperReport Error: Printer not found!");
    }}
    */
    }
    
}
