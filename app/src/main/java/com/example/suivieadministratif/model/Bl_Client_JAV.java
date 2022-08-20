package com.example.suivieadministratif.model;

import java.util.ArrayList;

public class Bl_Client_JAV {
    private String CodeClient;
    private String RaisonSociale;
    private String NumeroPiece;
    private double MontantTTC;

    ArrayList<Article_BL_JAV> list_art_par_bl ;

    public Bl_Client_JAV() {
    }

    public Bl_Client_JAV(String codeClient, String raisonSociale, String numeroPiece ) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        NumeroPiece = numeroPiece;

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




    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }




    public double getMontantTTC() {
        return MontantTTC;
    }

    public void setMontantTTC(double montantTTC) {
        MontantTTC = montantTTC;
    }


    public ArrayList<Article_BL_JAV> getList_art_par_bl() {
        return list_art_par_bl;
    }

    public void setList_art_par_bl(ArrayList<Article_BL_JAV> list_art_par_bl) {
        this.list_art_par_bl = list_art_par_bl;
    }

    @Override
    public String toString() {
        return "JournalArticleVendu{" +
                "CodeClient='" + CodeClient + '\'' +


                ", NumeroPiece='" + NumeroPiece + '\'' +

                ", MontantTTC=" + MontantTTC +

                '}';
    }
}
