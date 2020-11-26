package com.example.suivieadministratif.model;

import java.util.Date;

public class EcheanceClient {


   private  String  CodeClient   ;
    private  String   RaisonSociale   ;
    private  String   Libelle  ;
    private  String   Porteur  ;
    private  String    NumeroReglementClient  ;
    private  String    Reference  ;
    private  double   MontantRecu ;
    private Date Echeance ;
    private  String    NomUtilisateurActuel ;


    public EcheanceClient(String codeClient, String raisonSociale, String libelle, String porteur, String numeroReglementClient, String reference, double montantRecu, Date echeance, String nomUtilisateurActuel) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        Libelle = libelle;
        Porteur = porteur;
        NumeroReglementClient = numeroReglementClient;
        Reference = reference;
        MontantRecu = montantRecu;
        Echeance = echeance;
        NomUtilisateurActuel = nomUtilisateurActuel;
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

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getPorteur() {
        return Porteur;
    }

    public void setPorteur(String porteur) {
        Porteur = porteur;
    }

    public String getNumeroReglementClient() {
        return NumeroReglementClient;
    }

    public void setNumeroReglementClient(String numeroReglementClient) {
        NumeroReglementClient = numeroReglementClient;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public double getMontantRecu() {
        return MontantRecu;
    }

    public void setMontantRecu(double montantRecu) {
        MontantRecu = montantRecu;
    }

    public Date getEcheance() {
        return Echeance;
    }

    public void setEcheance(Date echeance) {
        Echeance = echeance;
    }

    public String getNomUtilisateurActuel() {
        return NomUtilisateurActuel;
    }

    public void setNomUtilisateurActuel(String nomUtilisateurActuel) {
        NomUtilisateurActuel = nomUtilisateurActuel;
    }

    @Override
    public String toString() {
        return "EcheanceClient{" +
                "CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", Libelle='" + Libelle + '\'' +
                ", Porteur='" + Porteur + '\'' +
                ", NumeroReglementClient='" + NumeroReglementClient + '\'' +
                ", Reference='" + Reference + '\'' +
                ", MontantRecu=" + MontantRecu +
                ", Echeance=" + Echeance +
                ", NomUtilisateurActuel='" + NomUtilisateurActuel + '\'' +
                '}';
    }
}
