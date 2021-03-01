package com.example.suivieadministratif.model;

import java.util.ArrayList;
import java.util.Date;

public class BonRetourVente {


    private String NumeroBonRetourVente;
    private Date   DateBonRetourVente;
    private String CodeClient;
    private String RaisonSociale;
    private String CodeModeReglement;
    private String CodeRepresentant;
    private String CodeDepot;
    private double TotalHT;
    private double TotalRemise;
    private double TotalNetHT;
    private double TotalTVA;
    private double TotalTTC;
    private double TotalNote;
    private String NumeroEtat;
    private String LibelleEtat;
    private String NomUtilisateur;
    private Date   DateCreation;
    private Date   HeureCreation;
    private String Observation;

    private ArrayList<LigneBonRetourVente> list_ligne_br ;

    public BonRetourVente ( String NumeroBonRetourVente, Date DateBonRetourVente, String referenceClient, double totalTTC ,double TotalRemise ,double TotalHT  ,double TotalTVA    , String NumeroEtat,String LibelleEtat) {
        this.NumeroBonRetourVente = NumeroBonRetourVente;
        this.DateBonRetourVente = DateBonRetourVente;
        RaisonSociale = referenceClient;

        TotalTTC = totalTTC;
        this.TotalRemise=TotalRemise  ;
        this.TotalHT=TotalHT ;
        this.TotalTVA=TotalTVA ;

        this.NumeroEtat = NumeroEtat  ;
        this.LibelleEtat=LibelleEtat;
    }




    public BonRetourVente(String numeroBonRetourVente , Date heureCreation, String codeClient, String raisonSociale, String  NomUtilisateur ,double TotalTTC , String numeroEtat) {
        NumeroBonRetourVente = numeroBonRetourVente;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        this.NomUtilisateur=   NomUtilisateur ;
        NumeroEtat = numeroEtat;
        this.TotalTTC =TotalTTC;
        HeureCreation = heureCreation;
    }

    public BonRetourVente(String numeroBonRetourVente, Date dateBonRetourVente, String codeClient, String raisonSociale, String codeModeReglement, String codeRepresentant, String codeDepot, double totalHT, double totalRemise, double totalNetHT, double totalTVA, double totalTTC, double totalNote, String numeroEtat, String nomUtilisateur, Date dateCreation, Date heureCreation, String observation) {
        NumeroBonRetourVente = numeroBonRetourVente;
        DateBonRetourVente = dateBonRetourVente;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        CodeModeReglement = codeModeReglement;
        CodeRepresentant = codeRepresentant;
        CodeDepot = codeDepot;
        TotalHT = totalHT;
        TotalRemise = totalRemise;
        TotalNetHT = totalNetHT;
        TotalTVA = totalTVA;
        TotalTTC = totalTTC;
        TotalNote = totalNote;
        NumeroEtat = numeroEtat;
        NomUtilisateur = nomUtilisateur;
        DateCreation = dateCreation;
        HeureCreation = heureCreation;
        Observation = observation;
    }

    public String getLibelleEtat() {
        return LibelleEtat;
    }

    public void setLibelleEtat(String libelleEtat) {
        LibelleEtat = libelleEtat;
    }

    public String getNumeroBonRetourVente() {
        return NumeroBonRetourVente;
    }

    public void setNumeroBonRetourVente(String numeroBonRetourVente) {
        NumeroBonRetourVente = numeroBonRetourVente;
    }

    public Date getDateBonRetourVente() {
        return DateBonRetourVente;
    }

    public void setDateBonRetourVente(Date dateBonRetourVente) {
        DateBonRetourVente = dateBonRetourVente;
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

    public String getCodeModeReglement() {
        return CodeModeReglement;
    }

    public void setCodeModeReglement(String codeModeReglement) {
        CodeModeReglement = codeModeReglement;
    }

    public String getCodeRepresentant() {
        return CodeRepresentant;
    }

    public void setCodeRepresentant(String codeRepresentant) {
        CodeRepresentant = codeRepresentant;
    }

    public double getTotalHT() {
        return TotalHT;
    }

    public void setTotalHT(double totalHT) {
        TotalHT = totalHT;
    }

    public double getTotalRemise() {
        return TotalRemise;
    }

    public void setTotalRemise(double totalRemise) {
        TotalRemise = totalRemise;
    }

    public double getTotalNetHT() {
        return TotalNetHT;
    }

    public void setTotalNetHT(double totalNetHT) {
        TotalNetHT = totalNetHT;
    }

    public double getTotalTVA() {
        return TotalTVA;
    }

    public void setTotalTVA(double totalTVA) {
        TotalTVA = totalTVA;
    }

    public double getTotalTTC() {
        return TotalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        TotalTTC = totalTTC;
    }

    public double getTotalNote() {
        return TotalNote;
    }

    public void setTotalNote(double totalNote) {
        TotalNote = totalNote;
    }

    public String getNumeroEtat() {
        return NumeroEtat;
    }

    public void setNumeroEtat(String numeroEtat) {
        NumeroEtat = numeroEtat;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    public Date getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        DateCreation = dateCreation;
    }

    public Date getHeureCreation() {
        return HeureCreation;
    }

    public void setHeureCreation(Date heureCreation) {
        HeureCreation = heureCreation;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public String getCodeDepot() {
        return CodeDepot;
    }

    public void setCodeDepot(String codeDepot) {
        CodeDepot = codeDepot;
    }


    public ArrayList<LigneBonRetourVente> getList_ligne_br() {
        return list_ligne_br;
    }

    public void setList_ligne_br(ArrayList<LigneBonRetourVente> list_ligne_br) {
        this.list_ligne_br = list_ligne_br;
    }

    @Override
    public String toString() {


        return " BonRetourVente {" +
                " NumeroBonRetourVente='" + NumeroBonRetourVente + '\'' +
                ", DateBonRetourVente=" + DateBonRetourVente +
                ", CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", CodeModeReglement='" + CodeModeReglement + '\'' +
                ", CodeRepresentant='" + CodeRepresentant + '\'' +
                ", TotalHT=" + TotalHT +
                ", TotalRemise=" + TotalRemise +
                ", TotalNetHT=" + TotalNetHT +
                ", TotalTVA=" + TotalTVA +
                ", TotalTTC=" + TotalTTC +
                ", TotalNote=" + TotalNote +
                ", NumeroEtat='" + NumeroEtat + '\'' +
                ", NomUtilisateur='" + NomUtilisateur + '\'' +
                ", DateCreation=" + DateCreation +
                ", HeureCreation=" + HeureCreation +
                ", Observation='" + Observation + '\'' +
                '}';

    }

}
