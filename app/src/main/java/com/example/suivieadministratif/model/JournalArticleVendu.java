package com.example.suivieadministratif.model;

public class JournalArticleVendu {


    private String CodeClient;
    private String RaisonSociale;
    private String RAD;
    private String REPRESENTANT;
    private String NumeroPiece;
    private String CodeArticle;
    private String DesignationArticle;
    private double MontantTTC;
    private int Quantite;

    public JournalArticleVendu(String codeClient, String raisonSociale, String RAD, String REPRESENTANT, String numeroPiece, String codeArticle, String designationArticle, double montantTTC, int quantite) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        this.RAD = RAD;
        this.REPRESENTANT = REPRESENTANT;
        NumeroPiece = numeroPiece;
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        MontantTTC = montantTTC;
        Quantite = quantite;
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

    public String getRAD() {
        return RAD;
    }

    public void setRAD(String RAD) {
        this.RAD = RAD;
    }

    public String getREPRESENTANT() {
        return REPRESENTANT;
    }

    public void setREPRESENTANT(String REPRESENTANT) {
        this.REPRESENTANT = REPRESENTANT;
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
                "CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", RAD='" + RAD + '\'' +
                ", REPRESENTANT='" + REPRESENTANT + '\'' +
                ", NumeroPiece='" + NumeroPiece + '\'' +
                ", CodeArticle='" + CodeArticle + '\'' +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                ", MontantTTC=" + MontantTTC +
                ", Quantite=" + Quantite +
                '}';
    }
}
