package com.example.suivieadministratif.model;

public class Article {

    private  String  CodeDepot ;
    private  String  CodeArticle  ;
    private  String  DesignationArticle ;
    private   double  PrixAchatHT ;
    private   double  MontantTTC ;
    private   double  PrixVenteTTC ;
    private   double    MontantTVA ;
    private   int  Quantite ;

    public Article(String codeArticle, String designationArticle) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
    }

    public Article(String codeDepot, String codeArticle, String designationArticle, double prixAchatHT,  double  MontantTTC   , double prixVenteTTC, double montantTVA, int quantite) {
        CodeDepot = codeDepot;
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        this.MontantTTC=MontantTTC   ;
        PrixAchatHT = prixAchatHT;
        PrixVenteTTC = prixVenteTTC;
        MontantTVA = montantTVA;
        Quantite = quantite;
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


    public double getMontantTTC() {
        return MontantTTC;
    }

    public void setMontantTTC(double montantTTC) {
        MontantTTC = montantTTC;
    }

    public String getCodeDepot() {
        return CodeDepot;
    }

    public void setCodeDepot(String codeDepot) {
        CodeDepot = codeDepot;
    }

    public double getPrixAchatHT() {
        return PrixAchatHT;
    }

    public void setPrixAchatHT(double prixAchatHT) {
        PrixAchatHT = prixAchatHT;
    }

    public double getPrixVenteTTC() {
        return PrixVenteTTC;
    }

    public void setPrixVenteTTC(double prixVenteTTC) {
        PrixVenteTTC = prixVenteTTC;
    }

    public double getMontantTVA() {
        return MontantTVA;
    }

    public void setMontantTVA(double montantTVA) {
        MontantTVA = montantTVA;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    @Override
    public String toString() {
        return "Article{" +
                "CodeArticle='" + CodeArticle + '\'' +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                '}';
    }
}
