package com.example.suivieadministratif.model;

public class ArticleAcheteNonVendu {


    private  String  CodeDepot   ;
    private   String  CodeArticle  ;
    private  String  Designation   ;
    private double  prixAchatHT   ;
    private double   MontantTVA ;
    private double MontantTTC  ;

    private  int   QtAchat   ;
    private   int   qunatite_stock_a_la_date ;
    private   int QTStock ;


    public ArticleAcheteNonVendu(String codeDepot, String codeArticle, String designation, double prixAchatHT, double montantTVA, double montantTTC, int qtAchat, int qunatite_stock_a_la_date, int QTStock) {
        CodeDepot = codeDepot;
        CodeArticle = codeArticle;
        Designation = designation;
        this.prixAchatHT = prixAchatHT;
        MontantTVA = montantTVA;
        MontantTTC = montantTTC;
        QtAchat = qtAchat;
        this.qunatite_stock_a_la_date = qunatite_stock_a_la_date;
        this.QTStock = QTStock;
    }

    public String getCodeDepot() {
        return CodeDepot;
    }

    public void setCodeDepot(String codeDepot) {
        CodeDepot = codeDepot;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        CodeArticle = codeArticle;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public int getQtAchat() {
        return QtAchat;
    }

    public void setQtAchat(int qtAchat) {
        QtAchat = qtAchat;
    }

    public double getPrixAchatHT() {
        return prixAchatHT;
    }

    public void setPrixAchatHT(double prixAchatHT) {
        this.prixAchatHT = prixAchatHT;
    }

    public double getMontantTTC() {
        return MontantTTC;
    }

    public void setMontantTTC(double montantTTC) {
        MontantTTC = montantTTC;
    }

    public int getQunatite_stock_a_la_date() {
        return qunatite_stock_a_la_date;
    }

    public void setQunatite_stock_a_la_date(int qunatite_stock_a_la_date) {
        this.qunatite_stock_a_la_date = qunatite_stock_a_la_date;
    }

    public int getQTStock() {
        return QTStock;
    }

    public void setQTStock(int QTStock) {
        this.QTStock = QTStock;
    }

    public double getMontantTVA() {
        return MontantTVA;
    }

    public void setMontantTVA(double montantTVA) {
        MontantTVA = montantTVA;
    }

    @Override
    public String toString() {

        return "ArticleAcheteNonVendu{" +
                "CodeArticle='" + CodeArticle + '\'' +
                ", Designation='" + Designation + '\'' +
                ", QtAchat=" + QtAchat +
                ", prixAchatHT=" + prixAchatHT +
                ", MontantTTC=" + MontantTTC +
                ", qunatite_stock_a_la_date=" + qunatite_stock_a_la_date +
                '}';

    }
}
