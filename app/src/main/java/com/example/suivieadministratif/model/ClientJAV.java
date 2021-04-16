package com.example.suivieadministratif.model;

import java.util.ArrayList;

public class ClientJAV {


    private String CodeClient;
    private String RaisonSociale;
    private String RAD;
    private String REPRESENTANT;
    private  double TotalTTC ;

    private ArrayList<Bl_Client_JAV>  list_bl_par_client  ;

    public ClientJAV() {
    }

    public ClientJAV(String codeClient, String raisonSociale, String RAD, String REPRESENTANT  ) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        this.RAD = RAD;
        this.REPRESENTANT = REPRESENTANT;

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

    public String getRAD() {
        return RAD;
    }

    public void setRAD(String RAD) {
        this.RAD = RAD;
    }

    public String getREPRESENTANT() {
        return REPRESENTANT;
    }

    public void setREPRESENTANT(String REPRESENTANT) {
        this.REPRESENTANT = REPRESENTANT;
    }


    public double getTotalTTC() {
        return TotalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        TotalTTC = totalTTC;
    }

    public ArrayList<Bl_Client_JAV> getList_bl_par_client() {
        return list_bl_par_client;
    }

    public void setList_bl_par_client(ArrayList<Bl_Client_JAV> list_bl_par_client) {
        this.list_bl_par_client = list_bl_par_client;
    }

    @Override
    public String toString() {
        return "JournalArticleVendu{" +
                "CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", RAD='" + RAD + '\'' +
                ", REPRESENTANT='" + REPRESENTANT + '\'' +

                '}';
    }
}
