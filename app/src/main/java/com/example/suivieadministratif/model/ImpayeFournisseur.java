package com.example.suivieadministratif.model;

import java.util.Date;

public class ImpayeFournisseur {


    private Date   DateImpayeFournisseur;
    private String CodeFournisseur;
    private String RaisonSociale;
    private double MontantImpayeFournisseur;
    private String DesignationVille;
    private String NumeroImpayeFournisseur;


  public ImpayeFournisseur ( Date dateImpayeFournisseur, String codeFournisseur, String raisonSociale, double montantImpayeFournisseur, String designationVille, String numeroImpayeFournisseur) {
    DateImpayeFournisseur = dateImpayeFournisseur;
    CodeFournisseur = codeFournisseur;
    RaisonSociale = raisonSociale;
    MontantImpayeFournisseur = montantImpayeFournisseur;
    DesignationVille = designationVille;
    NumeroImpayeFournisseur = numeroImpayeFournisseur;
  }

  public Date getDateImpayeFournisseur() {
    return DateImpayeFournisseur;
  }

  public void setDateImpayeFournisseur(Date dateImpayeFournisseur) {
    DateImpayeFournisseur = dateImpayeFournisseur;
  }

  public String getCodeFournisseur() {
    return CodeFournisseur;
  }

  public void setCodeFournisseur(String codeFournisseur) {
    CodeFournisseur = codeFournisseur;
  }

  public String getRaisonSociale() {
    return RaisonSociale;
  }

  public void setRaisonSociale(String raisonSociale) {
    RaisonSociale = raisonSociale;
  }

  public double getMontantImpayeFournisseur() {
    return MontantImpayeFournisseur;
  }

  public void setMontantImpayeFournisseur(double montantImpayeFournisseur) {
    MontantImpayeFournisseur = montantImpayeFournisseur;
  }

  public String getDesignationVille() {
    return DesignationVille;
  }

  public void setDesignationVille(String designationVille) {
    DesignationVille = designationVille;
  }

  public String getNumeroImpayeFournisseur() {
    return NumeroImpayeFournisseur;
  }

  public void setNumeroImpayeFournisseur(String numeroImpayeFournisseur) {
    NumeroImpayeFournisseur = numeroImpayeFournisseur;
  }

  @Override
  public String toString() {
    return "ImpayeFournisseur{" +
            "DateImpayeFournisseur=" + DateImpayeFournisseur +
            ", CodeFournisseur='" + CodeFournisseur + '\'' +
            ", RaisonSociale='" + RaisonSociale + '\'' +
            ", MontantImpayeFournisseur=" + MontantImpayeFournisseur +
            ", DesignationVille='" + DesignationVille + '\'' +
            ", NumeroImpayeFournisseur='" + NumeroImpayeFournisseur + '\'' +
            '}';
  }


}
