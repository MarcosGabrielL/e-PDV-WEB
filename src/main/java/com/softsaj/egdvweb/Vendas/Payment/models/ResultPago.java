/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "resultpagos")
public class ResultPago {
    
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    
    public String reques;
    public String collectionId;
    public String collectionStatus;
    public String externalReference;
    public String paymentType;
    public String merchantOrderId;
    public String preferenceId;
     public String siteId;
    public String processingMode;
    public String merchantAccountId;
    public String attributes;

    public ResultPago() {
        super();
    }

    public ResultPago(Long id, String reques, String collectionId, String collectionStatus, String externalReference, String paymentType, String merchantOrderId, String preferenceId, String siteId, String processingMode, String merchantAccountId, String attributes) {
        this.id = id;
        this.reques = reques;
        this.collectionId = collectionId;
        this.collectionStatus = collectionStatus;
        this.externalReference = externalReference;
        this.paymentType = paymentType;
        this.merchantOrderId = merchantOrderId;
        this.preferenceId = preferenceId;
        this.siteId = siteId;
        this.processingMode = processingMode;
        this.merchantAccountId = merchantAccountId;
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReques() {
        return reques;
    }

    public void setReques(String reques) {
        this.reques = reques;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
    }

    public String getMerchantAccountId() {
        return merchantAccountId;
    }

    public void setMerchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
    
    
    
}
