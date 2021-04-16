package com.example.suivieadministratif.model;

public class LigneBonRedressement {


    private String NumeroBonRedressement;
    private String CodeArticle;
    private String DesignationArticle;
    private double PrixAchatHT;
    private double MontantTVA;
    private double MontantTTC;
    private int Quantite;

    public LigneBonRedressement(String numeroBonRedressement, String codeArticle, String designationArticle, double prixAchatHT, double montantTVA, double montantTTC, int quantite) {
        NumeroBonRedressement = numeroBonRedressement;
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        PrixAchatHT = prixAchatHT;
        MontantTVA = montantTVA;
        MontantTTC = montantTTC;
        Quantite = quantite;
    }


    public String getNumeroBonRedressement() {
        return NumeroBonRedressement;
    }

    public void setNumeroBonRedressement(String numeroBonRedressement) {
        NumeroBonRedressement = numeroBonRedressement;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        CodeArticle = codeArticle;
    }

    public String getDesignationArticle() {
        return DesignationArticle;
    }

    public void setDesignationArticle(String designationArticle) {
        DesignationArticle = designationArticle;
    }

    public double getPrixAchatHT() {
        return PrixAchatHT;
    }

    public void setPrixAchatHT(double prixAchatHT) {
        PrixAchatHT = prixAchatHT;
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

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    @Override
    public String toString() {

        return "LigneBonRedressement{" +
                "NumeroBonRedressement='" + NumeroBonRedressement + '\'' +
                ", CodeArticle='" + CodeArticle + '\'' +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                ", PrixAchatHT=" + PrixAchatHT +
                ", MontantTVA=" + MontantTVA +
                ", MontantTTC=" + MontantTTC +
                ", Quantite=" + Quantite +
                '}';
    }
}
