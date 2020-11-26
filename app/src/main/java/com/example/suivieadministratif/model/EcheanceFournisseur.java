package com.example.suivieadministratif.model;

import java.util.Date;

public class EcheanceFournisseur {

    private String LibelleCompte;
    private String Libelle;
    private String NumeroReglementFournisseur;
    private String CodeFournisseur;
    private String CodeCompte;
    private String Reference;
    private Date Echeance;
    private double MontantRecu;
    private String CodeModeReglement;
    private String RaisonSociale;
    private Date DateReglement;


    public EcheanceFournisseur (String libelleCompte, String libelle, String numeroReglementFournisseur, String codeFournisseur, String codeCompte, String reference, Date echeance, double montantRecu, String codeModeReglement, String raisonSociale, Date dateReglement) {
        LibelleCompte = libelleCompte;
        Libelle = libelle;
        NumeroReglementFournisseur = numeroReglementFournisseur;
        CodeFournisseur = codeFournisseur;
        CodeCompte = codeCompte;
        Reference = reference;
        Echeance = echeance;
        MontantRecu = montantRecu;
        CodeModeReglement = codeModeReglement;
        RaisonSociale = raisonSociale;
        DateReglement = dateReglement;
    }


    public String getLibelleCompte() {
        return LibelleCompte;
    }

    public void setLibelleCompte(String libelleCompte) {
        LibelleCompte = libelleCompte;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getNumeroReglementFournisseur() {
        return NumeroReglementFournisseur;
    }

    public void setNumeroReglementFournisseur(String numeroReglementFournisseur) {
        NumeroReglementFournisseur = numeroReglementFournisseur;
    }

    public String getCodeFournisseur() {
        return CodeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        CodeFournisseur = codeFournisseur;
    }

    public String getCodeCompte() {
        return CodeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        CodeCompte = codeCompte;
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

    public double getMontantRecu() {
        return MontantRecu;
    }

    public void setMontantRecu(double montantRecu) {
        MontantRecu = montantRecu;
    }

    public String getCodeModeReglement() {
        return CodeModeReglement;
    }

    public void setCodeModeReglement(String codeModeReglement) {
        CodeModeReglement = codeModeReglement;
    }

    public String getRaisonSociale() {
        return RaisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }

    public Date getDateReglement() {
        return DateReglement;
    }

    public void setDateReglement(Date dateReglement) {
        DateReglement = dateReglement;
    }


    @Override
    public String toString() {
        return "EcheanceFournisseur{" +
                "LibelleCompte='" + LibelleCompte + '\'' +
                ", Libelle='" + Libelle + '\'' +
                ", NumeroReglementFournisseur='" + NumeroReglementFournisseur + '\'' +
                ", CodeFournisseur='" + CodeFournisseur + '\'' +
                ", CodeCompte='" + CodeCompte + '\'' +
                ", Reference='" + Reference + '\'' +
                ", Echeance=" + Echeance +
                ", MontantRecu=" + MontantRecu +
                ", CodeModeReglement='" + CodeModeReglement + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", DateReglement=" + DateReglement +
                '}';
    }
}
