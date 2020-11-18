package com.example.suivieadministratif.model;

import java.util.Date;

public class ReglementFournisseur {


    private String NumeroReglementFournisseur;
    private Date dateReglementFournisseur;
    private String RaisonSociale;
    private double TotalPayement;
    private String NomUtilisateur;


    public ReglementFournisseur(String numeroReglementFournisseur, Date dateReglementFournisseur, String raisonSociale, double totalPayement, String nomUtilisateur) {
        NumeroReglementFournisseur = numeroReglementFournisseur;
        this.dateReglementFournisseur = dateReglementFournisseur;
        RaisonSociale = raisonSociale;
        TotalPayement = totalPayement;
        NomUtilisateur = nomUtilisateur;
    }

    public String getNumeroReglementFournisseur() {
        return NumeroReglementFournisseur;
    }

    public void setNumeroReglementFournisseur(String numeroReglementFournisseur) {
        NumeroReglementFournisseur = numeroReglementFournisseur;
    }

    public Date getDateReglementFournisseur() {
        return dateReglementFournisseur;
    }

    public void setDateReglementFournisseur(Date dateReglementFournisseur) {
        this.dateReglementFournisseur = dateReglementFournisseur;
    }

    public String getRaisonSociale() {
        return RaisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }

    public double getTotalPayement() {
        return TotalPayement;
    }

    public void setTotalPayement(double totalPayement) {
        TotalPayement = totalPayement;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    @Override
    public String toString() {
        return "ReglementFournisseur{" +
                "NumeroReglementFournisseur='" + NumeroReglementFournisseur + '\'' +
                ", dateReglementFournisseur=" + dateReglementFournisseur +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", TotalPayement=" + TotalPayement +
                ", NomUtilisateur='" + NomUtilisateur + '\'' +
                '}';
    }
}