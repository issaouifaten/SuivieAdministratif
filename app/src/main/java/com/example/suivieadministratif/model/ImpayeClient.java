package com.example.suivieadministratif.model;

import java.util.Date;

public class ImpayeClient {


    private Date DateImpaye ;

    private  String  CodeClient   ;
    private  String   RaisonSociale   ;

    private  String   ModeReglement  ;
    private  String   banque  ;
    private  String    Reference  ;
    private Date Echeance ;


    private  double   MontantImpaye  ;
    private  double   ResteAPayer  ;

    private  String   Etat  ;


    public ImpayeClient(Date dateImpaye, String codeClient, String raisonSociale, String modeReglement, String banque, String reference, Date echeance, double montantImpaye, double resteAPayer, String etat) {
        DateImpaye = dateImpaye;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        ModeReglement = modeReglement;
        this.banque = banque;
        Reference = reference;
        Echeance = echeance;
        MontantImpaye = montantImpaye;
        ResteAPayer = resteAPayer;
        Etat = etat;
    }

    public Date getDateImpaye() {
        return DateImpaye;
    }

    public void setDateImpaye(Date dateImpaye) {
        DateImpaye = dateImpaye;
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

    public String getModeReglement() {
        return ModeReglement;
    }

    public void setModeReglement(String modeReglement) {
        ModeReglement = modeReglement;
    }

    public String getBanque() {
        return banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
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

    public double getMontantImpaye() {
        return MontantImpaye;
    }

    public void setMontantImpaye(double montantImpaye) {
        MontantImpaye = montantImpaye;
    }

    public double getResteAPayer() {
        return ResteAPayer;
    }

    public void setResteAPayer(double resteAPayer) {
        ResteAPayer = resteAPayer;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }

    @Override
    public String toString() {
        return "ImpayeClient{" +
                "DateImpaye=" + DateImpaye +
                ", CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", ModeReglement='" + ModeReglement + '\'' +
                ", banque='" + banque + '\'' +
                ", Reference='" + Reference + '\'' +
                ", Echeance=" + Echeance +
                ", MontantImpaye=" + MontantImpaye +
                ", ResteAPayer=" + ResteAPayer +
                ", Etat='" + Etat + '\'' +
                '}';
    }


}
