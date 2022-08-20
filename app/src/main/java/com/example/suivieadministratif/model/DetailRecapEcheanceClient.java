package com.example.suivieadministratif.model;

import java.util.Date;

public class DetailRecapEcheanceClient {

    private String ModeReglement;
    private String NumReglement;
    private Date  Echeance ;
    private String CodeClient;
    private String RaisonSociale;
    private String Banque;
    private String Refrence;
    private double Montant;


    public DetailRecapEcheanceClient(String modeReglement, String numReglement, Date  Echeance  ,String codeClient, String raisonSociale, String banque, String refrence, double montant) {
        ModeReglement = modeReglement;
        NumReglement = numReglement;
        this.Echeance =Echeance  ;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        Banque = banque;
        Refrence = refrence;
        Montant = montant;
    }

    public String getModeReglement() {
        return ModeReglement;
    }

    public void setModeReglement(String modeReglement) {
        ModeReglement = modeReglement;
    }

    public String getNumReglement() {
        return NumReglement;
    }

    public void setNumReglement(String numReglement) {
        NumReglement = numReglement;
    }

    public String getCodeClient() {
        return CodeClient;
    }

    public void setCodeClient(String codeClient) {
        CodeClient = codeClient;
    }

    public String getRaisonSociale() {
        return RaisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }

    public String getBanque() {
        return Banque;
    }

    public void setBanque(String banque) {
        Banque = banque;
    }

    public String getRefrence() {
        return Refrence;
    }

    public void setRefrence(String refrence) {
        Refrence = refrence;
    }

    public double getMontant() {
        return Montant;
    }

    public void setMontant(double montant) {
        Montant = montant;
    }


    public Date getEcheance() {
        return Echeance;
    }

    public void setEcheance(Date echeance) {
        Echeance = echeance;
    }

    @Override
    public String toString() {
        return "DetailRecapEcheanceClient{" +
                "ModeReglement='" + ModeReglement + '\'' +
                ", NumReglement='" + NumReglement + '\'' +
                ", CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", Banque='" + Banque + '\'' +
                ", Refrence='" + Refrence + '\'' +
                ", Montant=" + Montant +
                '}';
    }
}
