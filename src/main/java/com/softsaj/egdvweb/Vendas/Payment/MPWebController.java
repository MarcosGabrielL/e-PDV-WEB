package com.softsaj.egdvweb.Vendas.Payment;

import com.mercadopago.exceptions.MPException;
import com.softsaj.egdvweb.Vendas.Payment.models.AutorizationCode;
import com.softsaj.egdvweb.Vendas.Payment.models.ResultPago;
import com.softsaj.egdvweb.Vendas.Payment.services.AutorizationCodeService;
import com.softsaj.egdvweb.Vendas.Payment.services.ResultPagoService;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Marcos
 */

@Controller
public class MPWebController {
    
    @Autowired
    private ResultPagoService vs;
    
    @Autowired
    private AutorizationCodeService aservice;

    @GetMapping("/generic")
    public ResponseEntity<Void> success(
            HttpServletRequest request,
            @RequestParam("collection_id") String collectionId,
            @RequestParam("collection_status") String collectionStatus,
            @RequestParam("external_reference") String externalReference,
            @RequestParam("payment_type") String paymentType,
            @RequestParam("merchant_order_id") String merchantOrderId,
            @RequestParam("preference_id") String preferenceId,
            @RequestParam("site_id") String siteId,
            @RequestParam("processing_mode") String processingMode,
            @RequestParam("merchant_account_id") String merchantAccountId,
            RedirectAttributes attributes)
            throws MPException {
        
        /*request :org.springframework.web.servlet.resource.ResourceUrlEncodingFilter$ResourceUrlEncodingRequestWrapper@5a998a97
         collectionId :1247733689
         collectionStatus :approved
         externalReference :null
         paymentType :credit_card
         merchantOrderId :4603133764
         preferenceId :69325226-f112b96f-d3cf-4ee4-98dc-4af359cf9482
         siteId :MLB
         processingMode :aggregator
         merchantAccountId :null
         attributes :{}*/
        
        //SalvaDados de Pagamento
        ResultPago pago = new ResultPago();
        
        try {
            pago.setAttributes(request.getInputStream().toString());
        } catch (IOException ex) {
            Logger.getLogger(MPWebController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pago.setCollectionId(collectionId);
        pago.setCollectionStatus(collectionStatus);
        pago.setExternalReference(externalReference);
        pago.setMerchantAccountId(merchantAccountId);
        pago.setMerchantOrderId(merchantOrderId);
        pago.setPaymentType(paymentType);
        pago.setPreferenceId(preferenceId);
        pago.setProcessingMode(processingMode);
        pago.setReques(siteId);
        pago.setSiteId(siteId);
        
        ResultPago newResultPago = vs.addResultPago(pago);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://emiele.herokuapp.com/index?resultpag="+collectionStatus)).build();
 
    }
    
    @GetMapping("/generic/Vendedor")
    public ResponseEntity<Void> successVendedor(
            HttpServletRequest request,
            @RequestParam("collection_id") String collectionId,
            @RequestParam("collection_status") String collectionStatus,
            @RequestParam("external_reference") String externalReference,
            @RequestParam("payment_type") String paymentType,
            @RequestParam("merchant_order_id") String merchantOrderId,
            @RequestParam("preference_id") String preferenceId,
            @RequestParam("site_id") String siteId,
            @RequestParam("processing_mode") String processingMode,
            @RequestParam("merchant_account_id") String merchantAccountId,
            RedirectAttributes attributes)
            throws MPException {
        
        /*request :org.springframework.web.servlet.resource.ResourceUrlEncodingFilter$ResourceUrlEncodingRequestWrapper@5a998a97
         collectionId :1247733689
         collectionStatus :approved
         externalReference :null
         paymentType :credit_card
         merchantOrderId :4603133764
         preferenceId :69325226-f112b96f-d3cf-4ee4-98dc-4af359cf9482
         siteId :MLB
         processingMode :aggregator
         merchantAccountId :null
         attributes :{}*/
        
        //SalvaDados de Pagamento
        ResultPago pago = new ResultPago();
        
        try {
            pago.setAttributes(request.getInputStream().toString());
        } catch (IOException ex) {
            Logger.getLogger(MPWebController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pago.setCollectionId(collectionId);
        pago.setCollectionStatus(collectionStatus);
        pago.setExternalReference(externalReference);
        pago.setMerchantAccountId(merchantAccountId);
        pago.setMerchantOrderId(merchantOrderId);
        pago.setPaymentType(paymentType);
        pago.setPreferenceId(preferenceId);
        pago.setProcessingMode(processingMode);
        pago.setReques(siteId);
        pago.setSiteId(siteId);
        
        ResultPago newResultPago = vs.addResultPago(pago);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://emiele.herokuapp.com/pedidos/track?resultpag="+collectionStatus+"&idvenda="+externalReference)).build();
 
    }

	 @GetMapping("/generic/oauth")
    public ResponseEntity<Void> success(
            @RequestParam("code") String code,
            @RequestParam("state") String state) {
        
        //https://...?code=CODE&state=RANDOM_ID
        //SalvaDados de Autorização
            AutorizationCode a = new AutorizationCode();
            a.setCode(code);
            a.setState(state);
            
            System.out.println("Code: "+code);
            System.out.println("state: "+state);
            
            aservice.addAutorizationCode(a);
       
       

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://emiele.herokuapp.com/billing/creat?code="+code+"&state="+state)).build();
 
    }   
}
