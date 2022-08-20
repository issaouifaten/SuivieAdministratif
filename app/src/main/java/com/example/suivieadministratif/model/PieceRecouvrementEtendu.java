package com.example.suivieadministratif.model;

import java.util.Date;

public class PieceRecouvrementEtendu {

    private   String  CodeClient    ;
    private   int     orderAffichage   ;

    private String NumeroPiece;
    private Date datePiece;
    private String libelle;
    private double montant;
    private double montantRegle;
    private double RestantDue ;

    private double MargeDt ;
    private double MargePourcent ;


    public PieceRecouvrementEtendu  (String codeClient, int orderAffichage, String numeroPiece, Date datePiece, String libelle, double montant, double montantRegle, double restantDue, double margeDt, double margePourcent) {
        CodeClient = codeClient;
        this.orderAffichage = orderAffichage;
        NumeroPiece = numeroPiece;
        this.datePiece = datePiece;
        this.libelle = libelle;
        this.montant = montant;
        this.montantRegle = montantRegle;
        RestantDue = restantDue;
        MargeDt = margeDt;
        MargePourcent = margePourcent;
    }

    public String getCodeClient() {
        return CodeClient;
    }

    public void setCodeClient(String codeClient) {
        CodeClient = codeClient;
    }

    public int getOrderAffichage() {
        return orderAffichage;
    }

    public void setOrderAffichage(int orderAffichage) {
        this.orderAffichage = orderAffichage;
    }

    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public Date getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(Date datePiece) {
        this.datePiece = datePiece;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getMontantRegle() {
        return montantRegle;
    }

    public void setMontantRegle(double montantRegle) {
        this.montantRegle = montantRegle;
    }

    public double getRestantDue() {
        return RestantDue;
    }

    public void setRestantDue(double restantDue) {
        RestantDue = restantDue;
    }

    public double getMargeDt() {
        return MargeDt;
    }

    public void setMargeDt(double margeDt) {
        MargeDt = margeDt;
    }

    public double getMargePourcent() {
        return MargePourcent;
    }

    public void setMargePourcent(double margePourcent) {
        MargePourcent = margePourcent;
    }

    @Override
    public String toString() {

        return "PieceRecouvrementEtendu{" +
                "CodeClient='" + CodeClient + '\'' +
                ", orderAffichage=" + orderAffichage +
                ", NumeroPiece='" + NumeroPiece + '\'' +
                ", datePiece=" + datePiece +
                ", libelle='" + libelle + '\'' +
                ", montant=" + montant +
                ", montantRegle=" + montantRegle +
                ", RestantDue=" + RestantDue +
                ", MargeDt=" + MargeDt +
                ", MargePourcent=" + MargePourcent +
                '}';
    }
}
