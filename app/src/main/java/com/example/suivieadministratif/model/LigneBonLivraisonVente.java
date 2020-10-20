package com.example.suivieadministratif.model;

public class LigneBonLivraisonVente {


    private String NumeroBonLivraisonVente;
    private String CodeArticle;
    private int    NumeroOrdre;
    private String DesignationArticle;
    private double PrixVenteHT;
    private int    Quantite;
    private double MontantHT;
    private double TauxRemise;

    private double MontantRemise;
    private double NetHT;
    private double TauxTVA;
    private double MontantTVA;
    private double MontantTTC;
    private double PrixAchatNet;
    private double NoteArticle;
    private double PrixUnitaireNetTTC;
    private String Observation;
    private String NumeroBonCommandeVente;

    private int QuantiteRetour ;


    public LigneBonLivraisonVente(String NumeroBonLivraisonVente, String codeArticle, int quantite, double montantTTC) {
       this.NumeroBonLivraisonVente = NumeroBonLivraisonVente;
        CodeArticle = codeArticle;
        Quantite = quantite;
        MontantTTC = montantTTC;
    }



    public LigneBonLivraisonVente(String codeArticle, String designationArticle, int quantite) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        Quantite = quantite;
    }


    //  constructeur de  retour
    public LigneBonLivraisonVente(String numeroBonLivraisonVente, String codeArticle, int numeroOrdre, String designationArticle, int quantite, int quantiteRetour ,double MontantTTC , double PrixVenteHT ,  double PrixAchatNet, double NoteArticle,  double TauxTVA ,double TauxRemise) {
        NumeroBonLivraisonVente = numeroBonLivraisonVente;
        CodeArticle = codeArticle;
        NumeroOrdre = numeroOrdre;
        DesignationArticle = designationArticle;
        Quantite = quantite;
        QuantiteRetour = quantiteRetour;
         this. MontantTTC =   MontantTTC ;
         this.PrixVenteHT=PrixVenteHT;
         this.PrixAchatNet=PrixAchatNet ;
         this.NoteArticle=NoteArticle ;
         this.TauxTVA=TauxTVA ;
        this.TauxRemise=TauxRemise ;
    }

    public LigneBonLivraisonVente(String numeroBonLivraisonVente, String codeArticle, int numeroOrdre, String designationArticle, double prixVenteHT, int quantite, double montantHT, double tauxRemise, double montantRemise, double netHT, double tauxTVA, double montantTVA, double montantTTC, double prixAchatNet, double noteArticle, double prixUnitaireNetTTC, String observation, String numeroBonCommandeVente) {
        NumeroBonLivraisonVente = numeroBonLivraisonVente;
        CodeArticle = codeArticle;
        NumeroOrdre = numeroOrdre;
        DesignationArticle = designationArticle;
        PrixVenteHT = prixVenteHT;
        Quantite = quantite;
        MontantHT = montantHT;
        TauxRemise = tauxRemise;
        MontantRemise = montantRemise;
        NetHT = netHT;
        TauxTVA = tauxTVA;
        MontantTVA = montantTVA;
        MontantTTC = montantTTC;
        PrixAchatNet = prixAchatNet;
        NoteArticle = noteArticle;
        PrixUnitaireNetTTC = prixUnitaireNetTTC;
        Observation = observation;
        NumeroBonCommandeVente = numeroBonCommandeVente;
    }

    public String getNumeroBonLivraisonVente() {
        return NumeroBonLivraisonVente;
    }

    public void setNumeroBonLivraisonVente(String numeroBonLivraisonVente) {
        NumeroBonLivraisonVente = numeroBonLivraisonVente;
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

    public String getNumeroBonCommandeVente() {
        return NumeroBonCommandeVente;
    }

    public void setNumeroBonCommandeVente(String numeroBonCommandeVente) {
        NumeroBonCommandeVente = numeroBonCommandeVente;
    }

    public int getQuantiteRetour() {
        return QuantiteRetour;
    }

    public void setQuantiteRetour(int quantiteRetour) {
        QuantiteRetour = quantiteRetour;
    }

    @Override
    public String toString() {
        return " LigneBonLivraisonVente{ " +
                "  NumeroBonLivraisonVente='" + NumeroBonLivraisonVente + '\'' +
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
                ", NumeroBonCommandeVente='" + NumeroBonCommandeVente + '\'' +
                '}';
    }
}
