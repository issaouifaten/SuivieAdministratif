package com.example.suivieadministratif.model;

import java.util.ArrayList;
import java.util.Date;

public class BonLivraisonVente {


    private String NumeroBonLivraisonVente;
    private Date DateBonLivraisonVente;
    private String CodeDepot;
    private String CodeClient;
    private String RaisonSociale;
    private String CodeModeReglement;
    private String CodeRepresentant;
    private double TotalHT;
    private double TotalRemise;
    private double TotalNetHT;
    private double TotalTVA;
    private double TotalTTC;
    private double TotalNote;
    private String NumeroEtat;
    private String LibelleEtat;
    private String NumeroEtatPayement;
    private String LibelleEtatPayement;
    private String NomUtilisateur;
    private Date DateCreation;
    private Date HeureCreation;
    private String Observation;
    private String CodeLiv;
    private String CodeForceVente;


    String NumeroBonGratuiteVente;
    String NumeroFactureVente;
    String NumeroEtatPaiment;
    boolean retour;
    boolean Draft;

    String EtabliePar;
    String DraftePar;
    String PayePar;

    double MontantRecuPayement;
    double ResteAPayer;

    ArrayList<LigneBonLivraisonVente> list_ligne_bl;


    public BonLivraisonVente ( String NumeroBonLivraisonVente, Date DateBonLivraisonVente, String referenceClient, double totalTTC,String NumeroEtat,String LibelleEtat) {
        this.NumeroBonLivraisonVente = NumeroBonLivraisonVente;
        this.DateBonLivraisonVente = DateBonLivraisonVente;
        RaisonSociale = referenceClient;
        TotalTTC = totalTTC;
        this.NumeroEtat = NumeroEtat  ;
        this.LibelleEtat = LibelleEtat ;
    }



    public BonLivraisonVente (String numeroBonLivraisonVente, Date heureCreation, String codeClient, String raisonSociale, double totalTTC, double MontantRecuPayement, String LibelleEtatPayement, String NumeroEtat, String CodeDepot
            , String NumeroBonGratuiteVente, String NumeroFactureVente, String NumeroEtatPaiment, boolean retour, boolean Draft
            , String EtabliePar, String DraftePar, String PayePar) {

        NumeroBonLivraisonVente = numeroBonLivraisonVente;
        HeureCreation = heureCreation;

        CodeClient = codeClient;
        RaisonSociale = raisonSociale;

        TotalTTC = totalTTC;
        this.LibelleEtatPayement = LibelleEtatPayement;
        this.NumeroEtat = NumeroEtat;
        this.CodeDepot = CodeDepot;


        this.NumeroBonGratuiteVente = NumeroBonGratuiteVente;
        this.NumeroFactureVente = NumeroFactureVente;
        this.NumeroEtatPaiment = NumeroEtatPaiment;
        this.retour = retour;
        this.Draft = Draft;
        this.EtabliePar = EtabliePar;
        this.DraftePar = DraftePar;
        this.PayePar = PayePar;

        this.MontantRecuPayement = MontantRecuPayement;
        this.ResteAPayer = TotalTTC - MontantRecuPayement;
    }

    public BonLivraisonVente(String numeroBonLivraisonVente, Date dateBonLivraisonVente, String CodeDepot, String codeClient, String raisonSociale, String codeModeReglement, String codeRepresentant, double totalHT, double totalRemise, double totalNetHT, double totalTVA, double totalTTC, double totalNote, String numeroEtat, String nomUtilisateur, Date dateCreation, Date heureCreation, String observation, String codeLiv, String CodeForceVente) {
        NumeroBonLivraisonVente = numeroBonLivraisonVente;
        DateBonLivraisonVente = dateBonLivraisonVente;
        this.CodeDepot = CodeDepot;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        CodeModeReglement = codeModeReglement;
        CodeRepresentant = codeRepresentant;
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
        CodeLiv = codeLiv;
        this.CodeForceVente = CodeForceVente;
    }


    public String getNumeroBonLivraisonVente() {
        return NumeroBonLivraisonVente;
    }

    public void setNumeroBonLivraisonVente(String numeroBonLivraisonVente) {
        NumeroBonLivraisonVente = numeroBonLivraisonVente;
    }

    public Date getDateBonLivraisonVente() {
        return DateBonLivraisonVente;
    }

    public void setDateBonLivraisonVente(Date dateBonLivraisonVente) {
        DateBonLivraisonVente = dateBonLivraisonVente;
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

    public String getCodeLiv() {
        return CodeLiv;
    }

    public void setCodeLiv(String codeLiv) {
        CodeLiv = codeLiv;
    }

    public String getCodeForceVente() {
        return CodeForceVente;
    }

    public void setCodeForceVente(String codeForceVente) {
        CodeForceVente = codeForceVente;
    }


    public String getCodeDepot() {
        return CodeDepot;
    }

    public void setCodeDepot(String codeDepot) {
        CodeDepot = codeDepot;
    }


    public String getNumeroEtatPayement() {
        return NumeroEtatPayement;
    }

    public void setNumeroEtatPayement(String numeroEtatPayement) {
        NumeroEtatPayement = numeroEtatPayement;
    }

    public void setList_ligne_bl(ArrayList<LigneBonLivraisonVente> list_ligne_bl) {
        this.list_ligne_bl = list_ligne_bl;
    }

    public ArrayList<LigneBonLivraisonVente> getList_ligne_bl() {
        return list_ligne_bl;
    }


    public String getNumeroBonGratuiteVente() {
        return NumeroBonGratuiteVente;
    }

    public void setNumeroBonGratuiteVente(String numeroBonGratuiteVente) {
        NumeroBonGratuiteVente = numeroBonGratuiteVente;
    }

    public String getNumeroFactureVente() {
        return NumeroFactureVente;
    }

    public void setNumeroFactureVente(String numeroFactureVente) {
        NumeroFactureVente = numeroFactureVente;
    }

    public String getNumeroEtatPaiment() {
        return NumeroEtatPaiment;
    }

    public void setNumeroEtatPaiment(String numeroEtatPaiment) {
        NumeroEtatPaiment = numeroEtatPaiment;
    }

    public boolean isRetour() {
        return retour;
    }

    public void setRetour(boolean retour) {
        this.retour = retour;
    }

    public boolean isDraft() {
        return Draft;
    }

    public void setDraft(boolean draft) {
        Draft = draft;
    }

    public String getLibelleEtatPayement() {
        return LibelleEtatPayement;
    }

    public void setLibelleEtatPayement(String libelleEtatPayement) {
        LibelleEtatPayement = libelleEtatPayement;
    }


    public String getEtabliePar() {
        return EtabliePar;
    }

    public void setEtabliePar(String etabliePar) {
        EtabliePar = etabliePar;
    }

    public String getDraftePar() {
        return DraftePar;
    }

    public void setDraftePar(String draftePar) {
        DraftePar = draftePar;
    }

    public String getPayePar() {
        return PayePar;
    }

    public void setPayePar(String payePar) {
        PayePar = payePar;
    }


    public double getMontantRecuPayement() {
        return MontantRecuPayement;
    }

    public void setMontantRecuPayement(double montantRecuPayement) {
        MontantRecuPayement = montantRecuPayement;
    }

    public double getResteAPayer() {
        return ResteAPayer;
    }

    public void setResteAPayer(double resteAPayer) {
        ResteAPayer = resteAPayer;
    }


    public String getLibelleEtat() {
        return LibelleEtat;
    }

    public void setLibelleEtat(String libelleEtat) {
        LibelleEtat = libelleEtat;
    }

    @Override
    public String toString() {
        return "BonLivraisonVente{" +
                "NumeroBonLivraisonVente='" + NumeroBonLivraisonVente + '\'' +
                ", DateBonLivraisonVente=" + DateBonLivraisonVente +
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
                ", CodeLiv='" + CodeLiv + '\'' +
                '}';
    }
}
