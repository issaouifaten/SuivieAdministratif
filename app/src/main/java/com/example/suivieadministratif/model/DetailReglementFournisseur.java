package com.example.suivieadministratif.model;

import java.util.Date;

public class DetailReglementFournisseur {

    private String NumeroReglementClient;
    private String CodeModeReglement;
    private String Reference;
    private Date Echeance;
    private Double MontantRecu;


    public DetailReglementFournisseur(String numeroReglementClient, String codeModeReglement, String reference, Date echeance, Double montantRecu) {
        NumeroReglementClient = numeroReglementClient;
        CodeModeReglement = codeModeReglement;
        Reference = reference;
        Echeance = echeance;
        MontantRecu = montantRecu;
    }


    public String getNumeroReglementClient() {
        return NumeroReglementClient;
    }

    public void setNumeroReglementClient(String numeroReglementClient) {
        NumeroReglementClient = numeroReglementClient;
    }

    public String getCodeModeReglement() {
        return CodeModeReglement;
    }

    public void setCodeModeReglement(String codeModeReglement) {
        CodeModeReglement = codeModeReglement;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public Date getEcheance() {
        return Echeance;
    }

    public void setEcheance(Date echeance) {
        Echeance = echeance;
    }

    public Double getMontantRecu() {
        return MontantRecu;
    }

    public void setMontantRecu(Double montantRecu) {
        MontantRecu = montantRecu;
    }

    @Override
    public String toString() {

        return "DetailReglementClient{" +
                "NumeroReglementClient='" + NumeroReglementClient + '\'' +
                ", CodeModeReglement='" + CodeModeReglement + '\'' +
                ", Reference='" + Reference + '\'' +
                ", Echeance=" + Echeance +
                ", MontantRecu=" + MontantRecu +
                '}';
    }


}
