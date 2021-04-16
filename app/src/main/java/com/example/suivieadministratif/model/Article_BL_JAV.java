package com.example.suivieadministratif.model;

public class Article_BL_JAV {



    private String NumeroPiece;
    private String CodeArticle;
    private String DesignationArticle;
    private double MontantTTC;
    private int Quantite;

    public Article_BL_JAV(  String numeroPiece, String codeArticle, String designationArticle, double montantTTC, int quantite) {

        NumeroPiece = numeroPiece;
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        MontantTTC = montantTTC;
        Quantite = quantite;
    }



    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
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

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    @Override
    public String toString() {
        return "JournalArticleVendu{" +

                ", NumeroPiece='" + NumeroPiece + '\'' +
                ", CodeArticle='" + CodeArticle + '\'' +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                ", MontantTTC=" + MontantTTC +
                ", Quantite=" + Quantite +
                '}';
    }
}
