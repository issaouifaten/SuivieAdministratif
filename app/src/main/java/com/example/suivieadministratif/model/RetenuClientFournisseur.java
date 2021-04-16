package com.example.suivieadministratif.model;

import java.util.Date;

public class RetenuClientFournisseur {

    private Date  dateRetenu  ;
    private  int   CodeRetenu ;
    private   double  tauxRetenu  ;

    private   String  CodeClient  ;
    private   String  raisonSociale ;
    private   double  Retenu  ;
    private   double Brut  ;
    private   double Net   ;


    public RetenuClientFournisseur(Date dateRetenu, int codeRetenu, double tauxRetenu, String codeClient, String raisonSociale, double retenu, double brut, double net) {
        this.dateRetenu = dateRetenu;
        CodeRetenu = codeRetenu;
        this.tauxRetenu = tauxRetenu;
        CodeClient = codeClient;
        this.raisonSociale = raisonSociale;
        Retenu = retenu;
        Brut = brut;
        Net = net;
    }


    public Date getDateRetenu() {
        return dateRetenu;
    }

    public void setDateRetenu(Date dateRetenu) {
        this.dateRetenu = dateRetenu;
    }


    public int getCodeRetenu() {
        return CodeRetenu;
    }

    public void setCodeRetenu(int codeRetenu) {
        CodeRetenu = codeRetenu;
    }

    public double getTauxRetenu() {
        return tauxRetenu;
    }

    public void setTauxRetenu(double tauxRetenu) {
        this.tauxRetenu = tauxRetenu;
    }

    public String getCodeClient() {
        return CodeClient;
    }

    public void setCodeClient(String codeClient) {
        CodeClient = codeClient;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public double getRetenu() {
        return Retenu;
    }

    public void setRetenu(double retenu) {
        Retenu = retenu;
    }

    public double getBrut() {
        return Brut;
    }

    public void setBrut(double brut) {
        Brut = brut;
    }

    public double getNet() {
        return Net;
    }

    public void setNet(double net) {
        Net = net;
    }


    @Override
    public String toString() {
        return "RetenuClient{" +
                "dateRetenu=" + dateRetenu +
                ", CodeReetenu=" + CodeRetenu +
                ", tauxRetenu=" + tauxRetenu +
                ", CodeClient='" + CodeClient + '\'' +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", Retenu=" + Retenu +
                ", Brut=" + Brut +
                ", Net=" + Net +
                '}';
    }
}
