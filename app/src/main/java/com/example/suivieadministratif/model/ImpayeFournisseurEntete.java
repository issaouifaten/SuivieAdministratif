package com.example.suivieadministratif.model;

import java.util.ArrayList;
import java.util.Date;

public class ImpayeFournisseurEntete {


    private String CodeFournisseur;
    private String RaisonSociale;
    private double MontantImpayeFournisseur;

    private ArrayList<ImpayeFournisseur>  list_imp_frns   ;


  public ImpayeFournisseurEntete() {
  }

  public ImpayeFournisseurEntete(String codeFournisseur, String raisonSociale, double montantImpayeFournisseur) {
    CodeFournisseur = codeFournisseur;
    RaisonSociale = raisonSociale;
    MontantImpayeFournisseur = montantImpayeFournisseur;
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

  public ArrayList<ImpayeFournisseur> getList_imp_frns() {
    return list_imp_frns;
  }

  public void setList_imp_frns(ArrayList<ImpayeFournisseur> list_imp_frns) {
    this.list_imp_frns = list_imp_frns;
  }

  @Override
  public String toString() {
    return "ImpayeFournisseur{" +
            ", CodeFournisseur='" + CodeFournisseur + '\'' +
            ", RaisonSociale='" + RaisonSociale + '\'' +
            ", MontantImpayeFournisseur=" + MontantImpayeFournisseur +
            '}';
  }


}
