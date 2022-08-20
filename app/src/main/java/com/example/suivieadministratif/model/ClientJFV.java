package com.example.suivieadministratif.model;

import java.util.ArrayList;

public class ClientJFV {

    private String CodeClient;
    private String RaisonSociale;
    private  double TotalTTC ;

    private ArrayList<FV_client_JFV>  liste_facture_parèclient ;


    public ClientJFV() {
    }

    public ClientJFV(String codeClient, String raisonSociale, double totalTTC) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        TotalTTC = totalTTC;
    }

    public ClientJFV(String codeClient, String raisonSociale) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
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

    public double getTotalTTC() {
        return TotalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        TotalTTC = totalTTC;
    }

    public ArrayList<FV_client_JFV> getListe_facture_parèclient() {
        return liste_facture_parèclient;
    }

    public void setListe_facture_parèclient(ArrayList<FV_client_JFV> liste_facture_parèclient) {
        this.liste_facture_parèclient = liste_facture_parèclient;
    }

    @Override
    public String toString() {
        return "ClientJFV{" +
                "CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", TotalTTC=" + TotalTTC +
                ", liste_facture_parèclient=" + liste_facture_parèclient +
                '}';
    }
}
