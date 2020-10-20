package com.example.suivieadministratif.model;

public class LigneBonRetourVente {


    private String NumeroBonRetourVente;
    private String CodeArticle;
    private int    NumeroOrdre;
    private String DesignationArticle;
    private double PrixVenteHT;
    private int    Quantite;
    private int    QuantiteLivraison;
    private double MontantHT;

    private double TauxRemise;

    private double MontantRemise;
    private double NetHT;
    private double TauxTVA;
    private double MontantTVA;
    private double MontantTTC;
    private double MontantTTCLivraison;
    private double PrixAchatNet;
    private double NoteArticle;
    private double PrixUnitaireNetTTC;
    private String Observation;
    private String NumeroBonLivraisonVente;


    public LigneBonRetourVente(String NumeroBonRetourVente, String codeArticle, int quantite, double montantTTC) {
        this.NumeroBonRetourVente = NumeroBonRetourVente;
        CodeArticle = codeArticle;
        Quantite = quantite;
        MontantTTC = montantTTC;
    }
    public LigneBonRetourVente(String codeArticle, String designationArticle, int quantite) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        Quantite = quantite;
    }

    public LigneBonRetourVente(String numeroBonRetourVente, String codeArticle, int numeroOrdre, String designationArticle, double prixVenteHT, int quantite,int    QuantiteLivraison , double montantHT, double tauxRemise, double montantRemise, double netHT, double tauxTVA, double montantTVA, double montantTTC,double MontantTTCLivraison , double prixAchatNet, double noteArticle, double prixUnitaireNetTTC, String observation, String numeroBonLivraisonVente) {
        NumeroBonRetourVente = numeroBonRetourVente;
        CodeArticle = codeArticle;
        NumeroOrdre = numeroOrdre;
        DesignationArticle = designationArticle;
        PrixVenteHT = prixVenteHT;
        Quantite = quantite;
        this.QuantiteLivraison  = QuantiteLivraison  ;
        MontantHT = montantHT;
        TauxRemise = tauxRemise;
        MontantRemise = montantRemise;
        NetHT = netHT;
        TauxTVA = tauxTVA;
        MontantTVA = montantTVA;
        MontantTTC = montantTTC;
        this.MontantTTCLivraison=MontantTTCLivraison;
        PrixAchatNet = prixAchatNet;
        NoteArticle = noteArticle;
        PrixUnitaireNetTTC = prixUnitaireNetTTC;
        Observation = observation;
        NumeroBonLivraisonVente = numeroBonLivraisonVente;
    }


    public String getNumeroBonRetourVente() {
        return NumeroBonRetourVente;
    }

    public void setNumeroBonRetourVente(String numeroBonRetourVente) {
        NumeroBonRetourVente = numeroBonRetourVente;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        CodeArticle = codeArticle;
    }

    public int getNumeroOrdre() {
        return NumeroOrdre;
    }

    public void setNumeroOrdre(int numeroOrdre) {
        NumeroOrdre = numeroOrdre;
    }

    public String getDesignationArticle() {
        return DesignationArticle;
    }

    public void setDesignationArticle(String designationArticle) {
        DesignationArticle = designationArticle;
    }

    public double getPrixVenteHT() {
        return PrixVenteHT;
    }

    public void setPrixVenteHT(double prixVenteHT) {
        PrixVenteHT = prixVenteHT;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    public double getMontantHT() {
        return MontantHT;
    }

    public void setMontantHT(double montantHT) {
        MontantHT = montantHT;
    }

    public double getTauxRemise() {
        return TauxRemise;
    }

    public void setTauxRemise(double tauxRemise) {
        TauxRemise = tauxRemise;
    }

    public double getMontantRemise() {
        return MontantRemise;
    }

    public void setMontantRemise(double montantRemise) {
        MontantRemise = montantRemise;
    }

    public double getNetHT() {
        return NetHT;
    }

    public void setNetHT(double netHT) {
        NetHT = netHT;
    }

    public double getTauxTVA() {
        return TauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        TauxTVA = tauxTVA;
    }

    public double getMontantTVA() {
        return MontantTVA;
    }

    public void setMontantTVA(double montantTVA) {
        MontantTVA = montantTVA;
    }

    public double getMontantTTC() {
        return MontantTTC;
    }

    public void setMontantTTC(double montantTTC) {
        MontantTTC = montantTTC;
    }

    public double getPrixAchatNet() {
        return PrixAchatNet;
    }

    public void setPrixAchatNet(double prixAchatNet) {
        PrixAchatNet = prixAchatNet;
    }

    public double getNoteArticle() {
        return NoteArticle;
    }

    public void setNoteArticle(double noteArticle) {
        NoteArticle = noteArticle;
    }

    public double getPrixUnitaireNetTTC() {
        return PrixUnitaireNetTTC;
    }

    public void setPrixUnitaireNetTTC(double prixUnitaireNetTTC) {
        PrixUnitaireNetTTC = prixUnitaireNetTTC;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public String getNumeroBonLivraisonVente() {
        return NumeroBonLivraisonVente;
    }

    public void setNumeroBonLivraisonVente(String numeroBonLivraisonVente) {
        NumeroBonLivraisonVente = numeroBonLivraisonVente;
    }


    public int getQuantiteLivraison() {
        return QuantiteLivraison;
    }

    public void setQuantiteLivraison(int quantiteLivraison) {
        QuantiteLivraison = quantiteLivraison;
    }

    public double getMontantTTCLivraison() {
        return MontantTTCLivraison;
    }

    public void setMontantTTCLivraison(double montantTTCLivraison) {
        MontantTTCLivraison = montantTTCLivraison;
    }

    @Override
    public String toString() {
        return "LigneBonRetourVente{" +
                "NumeroBonRetourVente='" + NumeroBonRetourVente + '\'' +
                ", CodeArticle='" + CodeArticle + '\'' +
                ", NumeroOrdre=" + NumeroOrdre +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                ", PrixVenteHT=" + PrixVenteHT +
                ", Quantite=" + Quantite +
                ", MontantHT=" + MontantHT +
                ", TauxRemise=" + TauxRemise +
                ", MontantRemise=" + MontantRemise +
                ", NetHT=" + NetHT +
                ", TauxTVA=" + TauxTVA +
                ", MontantTVA=" + MontantTVA +
                ", MontantTTC=" + MontantTTC +
                ", PrixAchatNet=" + PrixAchatNet +
                ", NoteArticle=" + NoteArticle +
                ", PrixUnitaireNetTTC=" + PrixUnitaireNetTTC +
                ", Observation='" + Observation + '\'' +
                ", NumeroBonLivraisonVente='" + NumeroBonLivraisonVente + '\'' +
                '}';
    }
}
