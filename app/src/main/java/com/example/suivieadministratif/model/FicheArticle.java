package com.example.suivieadministratif.model;

import java.util.Date;

public class FicheArticle {

    private Date datePiece ;
    private String Tiers ;
    private String Operation ;
    private String NumeroPiece  ;
    private String CodeDepot  ;
    private String CodeArticle ;
    private int Entree  ;
    private int Sortie  ;
    private int Solde;
    private Date HeureCration ;

    public FicheArticle(Date datePiece, String tiers, String operation, String numeroPiece, String codeDepot, String codeArticle, int entree, int sortie, int Solde, Date heureCration) {
        this.datePiece = datePiece;
        Tiers = tiers;
        Operation = operation;
        NumeroPiece = numeroPiece;
        CodeDepot = codeDepot;
        CodeArticle = codeArticle;
        Entree = entree;
        Sortie = sortie;
        this.Solde=Solde ;
        HeureCration = heureCration;
    }


    public Date getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(Date datePiece) {
        this.datePiece = datePiece;
    }

    public String getTiers() {
        return Tiers;
    }

    public void setTiers(String tiers) {
        Tiers = tiers;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public String getCodeDepot() {
        return CodeDepot;
    }

    public void setCodeDepot(String codeDepot) {
        CodeDepot = codeDepot;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        CodeArticle = codeArticle;
    }

    public int getEntree() {
        return Entree;
    }

    public void setEntree(int entree) {
        Entree = entree;
    }

    public int getSortie() {
        return Sortie;
    }

    public void setSortie(int sortie) {
        Sortie = sortie;
    }

    public Date getHeureCration() {
        return HeureCration;
    }

    public void setHeureCration(Date heureCration) {
        HeureCration = heureCration;
    }

    public int getSolde() {
        return Solde;
    }

    public void setSolde(int solde) {
        Solde = solde;
    }

    @Override
    public String toString() {
        return "FicheArticle{" +
                "datePiece=" + datePiece +
                ", Tiers='" + Tiers + '\'' +
                ", Operation='" + Operation + '\'' +
                ", NumeroPiece='" + NumeroPiece + '\'' +
                ", CodeDepot='" + CodeDepot + '\'' +
                ", CodeArticle='" + CodeArticle + '\'' +
                ", Entree=" + Entree +
                ", Sortie=" + Sortie +
                ", HeureCration=" + HeureCration +
                '}';
    }
}
