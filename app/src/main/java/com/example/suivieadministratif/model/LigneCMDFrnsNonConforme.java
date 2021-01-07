package com.example.suivieadministratif.model;

public class LigneCMDFrnsNonConforme {


    private String CodeArticle;
    private String Designation;
    int qtLivrer;
    int qtCMD;
    int qtStock;
    double MontantHT;
    double PrixAchatHT;


    public LigneCMDFrnsNonConforme(String codeArticle, String designation, int qtLivrer, int qtCMD, int qtStock, double montantHT, double prixAchatHT) {
        CodeArticle = codeArticle;
        Designation = designation;
        this.qtLivrer = qtLivrer;
        this.qtCMD = qtCMD;
        this.qtStock = qtStock;
        MontantHT = montantHT;
        PrixAchatHT = prixAchatHT;
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

    public int getQtLivrer() {
        return qtLivrer;
    }

    public void setQtLivrer(int qtLivrer) {
        this.qtLivrer = qtLivrer;
    }

    public int getQtCMD() {
        return qtCMD;
    }

    public void setQtCMD(int qtCMD) {
        this.qtCMD = qtCMD;
    }

    public int getQtStock() {
        return qtStock;
    }

    public void setQtStock(int qtStock) {
        this.qtStock = qtStock;
    }

    public double getMontantHT() {
        return MontantHT;
    }

    public void setMontantHT(double montantHT) {
        MontantHT = montantHT;
    }

    public double getPrixAchatHT() {
        return PrixAchatHT;
    }

    public void setPrixAchatHT(double prixAchatHT) {
        PrixAchatHT = prixAchatHT;
    }

    @Override
    public String toString() {
        return "LigneCMDFrnsNonConforme{" +
                "CodeArticle='" + CodeArticle + '\'' +
                ", Designation='" + Designation + '\'' +
                ", qtLivrer=" + qtLivrer +
                ", qtCMD=" + qtCMD +
                ", qtStock=" + qtStock +
                ", MontantHT=" + MontantHT +
                ", PrixAchatHT=" + PrixAchatHT +
                '}';
    }
}
