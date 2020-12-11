package com.example.suivieadministratif.model;

public class ArticleFaibleRotation {

    private double TauxRotation;
    private double TauxBenifice;
    private double TauxCA;
    private double PourCBenifice;
    private double Coeff;
    private String CodeDepot;
    private String CodeArticle;
    private int Quantite;
    private double PrixAchatHT;
  // private double ValeurStock;
    private int QuantiteVendu;
    private double ValeurVenteHT;
    private double ValeurAchatHT;
    private int AchatMoin20j;
    private double TauxRotationCalculer;
    private String Designation;
    private String CodeFamille;
    private String CodeFournisseur;
    private double PrixAchatHTArticle;
    private double PrixVenteHT;
    private double ValeurStockNonMouvementer;


    public ArticleFaibleRotation(double tauxRotation, double tauxBenifice, double tauxCA, double pourCBenifice, double coeff, String codeDepot, String codeArticle, int quantite, double prixAchatHT,   int quantiteVendu, double valeurVenteHT, double valeurAchatHT, int achatMoin20j, double tauxRotationCalculer, String designation, String codeFamille, String codeFournisseur, double prixAchatHTArticle, double prixVenteHT, double valeurStockNonMouvementer) {
        TauxRotation = tauxRotation;
        TauxBenifice = tauxBenifice;
        TauxCA = tauxCA;
        PourCBenifice = pourCBenifice;
        Coeff = coeff;
        CodeDepot = codeDepot;
        CodeArticle = codeArticle;
        Quantite = quantite;
        PrixAchatHT = prixAchatHT;
      // ValeurStock = valeurStock;
        QuantiteVendu = quantiteVendu;
        ValeurVenteHT = valeurVenteHT;
        ValeurAchatHT = valeurAchatHT;
        AchatMoin20j = achatMoin20j;
        TauxRotationCalculer = tauxRotationCalculer;
        Designation = designation;
        CodeFamille = codeFamille;
        CodeFournisseur = codeFournisseur;
        PrixAchatHTArticle = prixAchatHTArticle;
        PrixVenteHT = prixVenteHT;
        ValeurStockNonMouvementer = valeurStockNonMouvementer;
    }


    public double getTauxRotation() {
        return TauxRotation;
    }

    public void setTauxRotation(double tauxRotation) {
        TauxRotation = tauxRotation;
    }

    public double getTauxBenifice() {
        return TauxBenifice;
    }

    public void setTauxBenifice(double tauxBenifice) {
        TauxBenifice = tauxBenifice;
    }

    public double getTauxCA() {
        return TauxCA;
    }

    public void setTauxCA(double tauxCA) {
        TauxCA = tauxCA;
    }

    public double getPourCBenifice() {
        return PourCBenifice;
    }

    public void setPourCBenifice(double pourCBenifice) {
        PourCBenifice = pourCBenifice;
    }

    public double getCoeff() {
        return Coeff;
    }

    public void setCoeff(double coeff) {
        Coeff = coeff;
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

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }

    public double getPrixAchatHT() {
        return PrixAchatHT;
    }

    public void setPrixAchatHT(double prixAchatHT) {
        PrixAchatHT = prixAchatHT;
    }



    public int getQuantiteVendu() {
        return QuantiteVendu;
    }

    public void setQuantiteVendu(int quantiteVendu) {
        QuantiteVendu = quantiteVendu;
    }

    public double getValeurVenteHT() {
        return ValeurVenteHT;
    }

    public void setValeurVenteHT(double valeurVenteHT) {
        ValeurVenteHT = valeurVenteHT;
    }

    public double getValeurAchatHT() {
        return ValeurAchatHT;
    }

    public void setValeurAchatHT(double valeurAchatHT) {
        ValeurAchatHT = valeurAchatHT;
    }

    public int getAchatMoin20j() {
        return AchatMoin20j;
    }

    public void setAchatMoin20j(int achatMoin20j) {
        AchatMoin20j = achatMoin20j;
    }

    public double getTauxRotationCalculer() {
        return TauxRotationCalculer;
    }

    public void setTauxRotationCalculer(double tauxRotationCalculer) {
        TauxRotationCalculer = tauxRotationCalculer;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getCodeFamille() {
        return CodeFamille;
    }

    public void setCodeFamille(String codeFamille) {
        CodeFamille = codeFamille;
    }

    public String getCodeFournisseur() {
        return CodeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        CodeFournisseur = codeFournisseur;
    }

    public double getPrixAchatHTArticle() {
        return PrixAchatHTArticle;
    }

    public void setPrixAchatHTArticle(double prixAchatHTArticle) {
        PrixAchatHTArticle = prixAchatHTArticle;
    }

    public double getPrixVenteHT() {
        return PrixVenteHT;
    }

    public void setPrixVenteHT(double prixVenteHT) {
        PrixVenteHT = prixVenteHT;
    }

    public double getValeurStockNonMouvementer() {
        return ValeurStockNonMouvementer;
    }

    public void setValeurStockNonMouvementer(double valeurStockNonMouvementer) {
        ValeurStockNonMouvementer = valeurStockNonMouvementer;
    }

    @Override
    public String toString() {

        return "ArticleFaibleRotation{" +
                "TauxRotation=" + TauxRotation +
                ", TauxBenifice=" + TauxBenifice +
                ", TauxCA=" + TauxCA +
                ", PourCBenifice=" + PourCBenifice +
                ", Coeff=" + Coeff +
                ", CodeDepot='" + CodeDepot + '\'' +
                ", CodeArticle=" + CodeArticle +
                ", Quantite=" + Quantite +
                ", PrixAchatHT=" + PrixAchatHT +

                ", QuantiteVendu=" + QuantiteVendu +
                ", ValeurVenteHT=" + ValeurVenteHT +
                ", ValeurAchatHT=" + ValeurAchatHT +
                ", AchatMoin20j=" + AchatMoin20j +
                ", TauxRotationCalculer=" + TauxRotationCalculer +
                ", Designation='" + Designation + '\'' +
                ", CodeFamille='" + CodeFamille + '\'' +
                ", CodeFournisseur='" + CodeFournisseur + '\'' +
                ", PrixAchatHTArticle=" + PrixAchatHTArticle +
                ", PrixVenteHT=" + PrixVenteHT +
                ", ValeurStockNonMouvementer=" + ValeurStockNonMouvementer +
                '}';
    }
}
