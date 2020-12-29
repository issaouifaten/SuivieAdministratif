package com.example.suivieadministratif.model;

import java.util.ArrayList;
import java.util.Date;

public class SuiviCMD_frns {

    private String NumeroBonAchat;
    private Date DateBonCommandeAchat;
    private String CodeFournisseur;
    private String RaisonSocial;
    private double TotalHT;

    private ArrayList<LigneSuiviCMD_frns> listLigneSuiviCMD_frns ;

    public SuiviCMD_frns(String numeroBonAchat, Date dateBonCommandeAchat, String CodeFournisseur, String raisonSocial, double totalHT) {
        NumeroBonAchat = numeroBonAchat;
        DateBonCommandeAchat = dateBonCommandeAchat;
        this.CodeFournisseur = CodeFournisseur;
        RaisonSocial = raisonSocial;
        TotalHT = totalHT;
    }

    public String getNumeroBonAchat() {
        return NumeroBonAchat;
    }

    public void setNumeroBonAchat(String numeroBonAchat) {
        NumeroBonAchat = numeroBonAchat;
    }

    public Date getDateBonCommandeAchat() {
        return DateBonCommandeAchat;
    }

    public void setDateBonCommandeAchat(Date dateBonCommandeAchat) {
        DateBonCommandeAchat = dateBonCommandeAchat;
    }


    public String getCodeFournisseur() {
        return CodeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        CodeFournisseur = codeFournisseur;
    }

    public String getRaisonSocial() {
        return RaisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        RaisonSocial = raisonSocial;
    }

    public double getTotalHT() {
        return TotalHT;
    }

    public void setTotalHT(double totalHT) {
        TotalHT = totalHT;
    }


    public ArrayList<LigneSuiviCMD_frns> getListLigneSuiviCMD_frns() {
        return listLigneSuiviCMD_frns;
    }

    public void setListLigneSuiviCMD_frns(ArrayList<LigneSuiviCMD_frns> listLigneSuiviCMD_frns) {
        this.listLigneSuiviCMD_frns = listLigneSuiviCMD_frns;
    }

    @Override
    public String toString() {
        return "SuiviCMD_frns{" +
                "NumeroBonAchat='" + NumeroBonAchat + '\'' +
                ", DateBonCommandeAchat=" + DateBonCommandeAchat +
                ", CodeFournisseur ='" + CodeFournisseur + '\'' +
                ", RaisonSocial='" + RaisonSocial + '\'' +
                ", TotalHT=" + TotalHT +
                '}';
    }
}
