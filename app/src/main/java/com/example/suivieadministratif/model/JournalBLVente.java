package com.example.suivieadministratif.model;

import java.util.Date;

public class JournalBLVente {

   private String  NumeroPiece  ;
   private Date DatePiece ;
    private String  CodeArticle  ;
    private String DesignationArticle   ;
    private int Quantite  ;
    private String    CodeClient   ;
    private String RaisonSociale ;
    private String  CodeFournisseur  ;
    private String  Frs   ;
    private double  MontantHT  ;
    private double  MontantRemise  ;
    private double   MontantTVA   ;
    private double    MontantTTC  ;
    private double Benifice  ;
    private double rot  ;
    private double     PrixRevient  ;
    private double   BenificeNET ;

    public JournalBLVente(String numeroPiece, Date datePiece, String codeArticle, String designationArticle, int quantite, String codeClient, String raisonSociale, String codeFournisseur, String frs, double montantHT, double montantRemise, double montantTVA, double montantTTC, double benifice, double rot, double prixRevient, double benificeNET) {
        NumeroPiece = numeroPiece;
        DatePiece = datePiece;
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        Quantite = quantite;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        CodeFournisseur = codeFournisseur;
        Frs = frs;
        MontantHT = montantHT;
        MontantRemise = montantRemise;
        MontantTVA = montantTVA;
        MontantTTC = montantTTC;
        Benifice = benifice;
        this.rot = rot;
        PrixRevient = prixRevient;
        BenificeNET = benificeNET;
    }

    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public Date getDatePiece() {
        return DatePiece;
    }

    public void setDatePiece(Date datePiece) {
        DatePiece = datePiece;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        CodeArticle = codeArticle;
    }

    public String getDesignationArticle() {
        return DesignationArticle;
    }

    public void setDesignationArticle(String designationArticle) {
        DesignationArticle = designationArticle;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
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

    public String getCodeFournisseur() {
        return CodeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        CodeFournisseur = codeFournisseur;
    }

    public String getFrs() {
        return Frs;
    }

    public void setFrs(String frs) {
        Frs = frs;
    }

    public double getMontantHT() {
        return MontantHT;
    }

    public void setMontantHT(double montantHT) {
        MontantHT = montantHT;
    }

    public double getMontantRemise() {
        return MontantRemise;
    }

    public void setMontantRemise(double montantRemise) {
        MontantRemise = montantRemise;
    }

    public double getMontantTVA() {
        return MontantTVA;
    }

    public void setMontantTVA(double montantTVA) {
        MontantTVA = montantTVA;
    }

    public double getMontantTTC() {
        return MontantTTC;
    }

    public void setMontantTTC(double montantTTC) {
        MontantTTC = montantTTC;
    }

    public double getBenifice() {
        return Benifice;
    }

    public void setBenifice(double benifice) {
        Benifice = benifice;
    }

    public double getRot() {
        return rot;
    }

    public void setRot(double rot) {
        this.rot = rot;
    }

    public double getPrixRevient() {
        return PrixRevient;
    }

    public void setPrixRevient(double prixRevient) {
        PrixRevient = prixRevient;
    }

    public double getBenificeNET() {
        return BenificeNET;
    }

    public void setBenificeNET(double benificeNET) {
        BenificeNET = benificeNET;
    }

    @Override
    public String toString() {
        return "JournalBLVente{" +
                "NumeroPiece='" + NumeroPiece + '\'' +
                ", DatePiece=" + DatePiece +
                ", CodeArticle='" + CodeArticle + '\'' +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                ", Quantite=" + Quantite +
                ", CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", CodeFournisseur='" + CodeFournisseur + '\'' +
                ", Frs='" + Frs + '\'' +
                ", MontantHT=" + MontantHT +
                ", MontantRemise=" + MontantRemise +
                ", MontantTVA=" + MontantTVA +
                ", MontantTTC=" + MontantTTC +
                ", Benifice=" + Benifice +
                ", rot=" + rot +
                ", PrixRevient=" + PrixRevient +
                ", BenificeNET=" + BenificeNET +
                '}';
    }
}
