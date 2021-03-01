package com.example.suivieadministratif.model;

import java.util.Date;

public class MouvementVenteService {


    private String CodeClient;
    private String RaisonSociale;
    private String NumeroPiece;
    private String CodeArticle;
    private String DesignationArticle;
    private Date DatePiece;
    private int Quantite;
    private double MontantTTC;
    private String NomUtilisateur;


    public MouvementVenteService(  String codeClient, String raisonSociale, String numeroPiece, String codeArticle, String designationArticle, Date datePiece, int quantite, double montantTTC, String nomUtilisateur) {

        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        NumeroPiece = numeroPiece;
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        DatePiece = datePiece;
        Quantite = quantite;
        MontantTTC = montantTTC;
        NomUtilisateur = nomUtilisateur;
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

    public Date getDatePiece() {
        return DatePiece;
    }

    public void setDatePiece(Date datePiece) {
        DatePiece = datePiece;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    public double getMontantTTC() {
        return MontantTTC;
    }

    public void setMontantTTC(double montantTTC) {
        MontantTTC = montantTTC;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    @Override
    public String toString() {
        return "MouvementVenteService{" +

                ", CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", NumeroPiece='" + NumeroPiece + '\'' +
                ", CodeArticle='" + CodeArticle + '\'' +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                ", DatePiece=" + DatePiece +
                ", Quantite=" + Quantite +
                ", MontantTTC=" + MontantTTC +
                ", NomUtilisateur='" + NomUtilisateur + '\'' +
                '}';
    }
}
