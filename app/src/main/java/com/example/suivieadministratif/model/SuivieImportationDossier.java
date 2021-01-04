package com.example.suivieadministratif.model;

import java.util.Date;

public class SuivieImportationDossier {


    private   String NumeroFactureAchat ;
    private   String RaisonSociale ;
    private   Date DateAchatDiver ;
    private   double TotalNetHT ;
    private   double TotalTVA ;
    private   double  TimbreFiscal ;
    private   double    TotalTTC;


    public SuivieImportationDossier(String numeroFactureAchat, String raisonSociale, Date dateAchatDiver, double totalNetHT, double totalTVA, double timbreFiscal, double totalTTC) {
        NumeroFactureAchat = numeroFactureAchat;
        RaisonSociale = raisonSociale;
        DateAchatDiver = dateAchatDiver;
        TotalNetHT = totalNetHT;
        TotalTVA = totalTVA;
        TimbreFiscal = timbreFiscal;
        TotalTTC = totalTTC;
    }


    public String getNumeroFactureAchat() {
        return NumeroFactureAchat;
    }

    public void setNumeroFactureAchat(String numeroFactureAchat) {
        NumeroFactureAchat = numeroFactureAchat;
    }

    public String getRaisonSociale() {
        return RaisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }

    public Date getDateAchatDiver() {
        return DateAchatDiver;
    }

    public void setDateAchatDiver(Date dateAchatDiver) {
        DateAchatDiver = dateAchatDiver;
    }

    public double getTotalNetHT() {
        return TotalNetHT;
    }

    public void setTotalNetHT(double totalNetHT) {
        TotalNetHT = totalNetHT;
    }

    public double getTotalTVA() {
        return TotalTVA;
    }

    public void setTotalTVA(double totalTVA) {
        TotalTVA = totalTVA;
    }

    public double getTimbreFiscal() {
        return TimbreFiscal;
    }

    public void setTimbreFiscal(double timbreFiscal) {
        TimbreFiscal = timbreFiscal;
    }

    public double getTotalTTC() {
        return TotalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        TotalTTC = totalTTC;
    }


    @Override
    public String toString() {
        return "SuivieImportationDossier{" +
                "NumeroFactureAchat='" + NumeroFactureAchat + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", DateAchatDiver='" + DateAchatDiver + '\'' +
                ", TotalNetHT=" + TotalNetHT +
                ", TotalTVA=" + TotalTVA +
                ", TimbreFiscal=" + TimbreFiscal +
                ", TotalTTC=" + TotalTTC +
                '}';
    }


}
