package com.example.suivieadministratif.model;

import java.util.Date;

public class SoldeClient {
    private String CodeClient;
    private String RaisonSocial;
    private double debit;
    private double credit;
    private double solde;
    private double TotalDraft;
    int nbrClick;


    public SoldeClient(String codeClient, double solde) {
        CodeClient = codeClient;
        this.solde = solde;
    }

    private   String Recouvreur ;
    private   String Livreur ;


    private  String  LivreePar  ;
    private  Date dateLivraison  ;

    private   String  RetourPar  ;
    private   Date dateRetour ;

    private   String CouvertPar  ;
    private   Date dateRecouvrement ;



    public SoldeClient(String codeClient, String raisonSocial, double solde, String recouvreur, String livreur, int nbrClick) {
        CodeClient = codeClient;
        RaisonSocial = raisonSocial;

        this.solde = solde;

        Recouvreur = recouvreur;
        Livreur = livreur;
        this.nbrClick = nbrClick;
    }

    // pour livraison

    public SoldeClient(String codeClient, String raisonSocial, double solde, int nbrClick, String livreePar, Date dateLivraison) {
        CodeClient = codeClient;
        RaisonSocial = raisonSocial;
        this.solde = solde;
        this.nbrClick = nbrClick;
        LivreePar = livreePar;
        this.dateLivraison = dateLivraison;
    }


    //  pour retour

    public SoldeClient(String codeClient, String raisonSocial, double solde, String retourPar, Date dateRetour , int nbrClick) {
        CodeClient = codeClient;
        RaisonSocial = raisonSocial;
        this.solde = solde;
        this.nbrClick = nbrClick;
        RetourPar = retourPar;
        this.dateRetour = dateRetour;
    }

    /**  pour draft **/
    public SoldeClient(String codeClient, String raisonSocial, String CouvertPar, Date dateRecouvrement  , double solde , double TotalDraft, int nbrClick) {
        CodeClient = codeClient;
        RaisonSocial = raisonSocial;
        this.solde = solde;
        this.TotalDraft=TotalDraft ;
        this.nbrClick = nbrClick;
        this.CouvertPar = CouvertPar;
        this.dateRecouvrement = dateRecouvrement;
    }





    public SoldeClient(String codeClient, String raisonSocial, double solde, String recouvreur, String livreur) {
        CodeClient = codeClient;
        RaisonSocial = raisonSocial;
        this.solde = solde;
        Recouvreur = recouvreur;
        Livreur = livreur;
    }


    /** fin constructeur  CHEF ZONE**/
    public SoldeClient(String codeClient, String raisonSocial, double solde) {
        CodeClient = codeClient;
        RaisonSocial = raisonSocial;
        this.solde = solde;
    }

    public SoldeClient(String codeClient, String raisonSocial, double solde , int nbrClick) {
        CodeClient = codeClient;
        RaisonSocial = raisonSocial;
        this.solde = solde;
        this.nbrClick = nbrClick ;
    }

    public SoldeClient(String codeClient, String raisonSocial, double debit, double credit) {
        CodeClient = codeClient;
        RaisonSocial = raisonSocial;
        this.debit = debit;
        this.credit = credit;
        this.solde = debit - credit;
    }


    public String getCodeClient() {
        return CodeClient;
    }

    public void setCodeClient(String codeClient) {
        CodeClient = codeClient;
    }

    public String getRaisonSocial() {
        return RaisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        RaisonSocial = raisonSocial;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getNbrClick() {
        return nbrClick;
    }

    public void setNbrClick(int nbrClick) {
        this.nbrClick = nbrClick;
    }


    public String getRecouvreur() {
        return Recouvreur;
    }

    public void setRecouvreur(String recouvreur) {
        Recouvreur = recouvreur;
    }

    public String getLivreur() {
        return Livreur;
    }

    public void setLivreur(String livreur) {
        Livreur = livreur;
    }


    public String getLivreePar() {
        return LivreePar;
    }

    public void setLivreePar(String livreePar) {
        LivreePar = livreePar;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getRetourPar() {
        return RetourPar;
    }

    public void setRetourPar(String retourPar) {
        RetourPar = retourPar;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }


    public String getCouvertPar() {
        return CouvertPar;
    }

    public void setCouvertPar(String CouvertPar) {
        CouvertPar = CouvertPar;
    }

    public Date getDateRecouvrement() {
        return dateRecouvrement;
    }

    public void setDateRecouvrement(Date dateRecouvrement) {
        this.dateRecouvrement = dateRecouvrement;
    }

    public double getTotalDraft() {
        return TotalDraft;
    }

    public void setTotalDraft(double totalDraft) {
        TotalDraft = totalDraft;
    }

    @Override
    public String toString() {
        return "SoldeClient{" +
                "CodeClient='" + CodeClient + '\'' +
                ", RaisonSocial='" + RaisonSocial + '\'' +
                ", debit=" + debit +
                ", credit=" + credit +
                ", solde=" + solde +
                ", nbrClick=" + nbrClick +
                ", Recouvreur='" + Recouvreur + '\'' +
                ", Livreur='" + Livreur + '\'' +
                '}';
    }

}
