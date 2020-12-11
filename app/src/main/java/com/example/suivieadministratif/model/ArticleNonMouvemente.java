package com.example.suivieadministratif.model;

public class ArticleNonMouvemente {


    private  int Etrange ;
    private   String  CodeDepot  ;
    private  String CodeArticle  ;
    private int QuantiteInitiale ;
    private  int QuantiteAchete  ;
    private  String CodeFamille  ;
    private   String Libelle  ;
    private  String CodeFournisseur  ;
    private   String RaisonSociale ;
    private double  PrixAchatHT  ;
    private   String Designation  ;
    private  double valeurHT  ;

    public ArticleNonMouvemente(int etrange, String codeDepot, String codeArticle, int quantiteInitiale, int quantiteAchete, String codeFamille, String libelle, String codeFournisseur, String raisonSociale, double prixAchatHT, String designation, double valeurHT) {
        Etrange = etrange;
        CodeDepot = codeDepot;
        CodeArticle = codeArticle;
        QuantiteInitiale = quantiteInitiale;
        QuantiteAchete = quantiteAchete;
        CodeFamille = codeFamille;
        Libelle = libelle;
        CodeFournisseur = codeFournisseur;
        RaisonSociale = raisonSociale;
        PrixAchatHT = prixAchatHT;
        Designation = designation;
        this.valeurHT = valeurHT;
    }


    public int getEtrange() {
        return Etrange;
    }

    public void setEtrange(int etrange) {
        Etrange = etrange;
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

    public int getQuantiteInitiale() {
        return QuantiteInitiale;
    }

    public void setQuantiteInitiale(int quantiteInitiale) {
        QuantiteInitiale = quantiteInitiale;
    }

    public int getQuantiteAchete() {
        return QuantiteAchete;
    }

    public void setQuantiteAchete(int quantiteAchete) {
        QuantiteAchete = quantiteAchete;
    }

    public String getCodeFamille() {
        return CodeFamille;
    }

    public void setCodeFamille(String codeFamille) {
        CodeFamille = codeFamille;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getCodeFournisseur() {
        return CodeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        CodeFournisseur = codeFournisseur;
    }

    public String getRaisonSociale() {
        return RaisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }

    public double getPrixAchatHT() {
        return PrixAchatHT;
    }

    public void setPrixAchatHT(double prixAchatHT) {
        PrixAchatHT = prixAchatHT;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public double getValeurHT() {
        return valeurHT;
    }

    public void setValeurHT(double valeurHT) {
        this.valeurHT = valeurHT;
    }

    @Override
    public String toString() {
        return "ArticleNonMouvemente{" +
                "Etrange=" + Etrange +
                ", CodeDepot='" + CodeDepot + '\'' +
                ", CodeArticle='" + CodeArticle + '\'' +
                ", QuantiteInitiale=" + QuantiteInitiale +
                ", QuantiteAchete=" + QuantiteAchete +
                ", CodeFamille='" + CodeFamille + '\'' +
                ", Libelle='" + Libelle + '\'' +
                ", CodeFournisseur='" + CodeFournisseur + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", PrixAchatHT=" + PrixAchatHT +
                ", Designation='" + Designation + '\'' +
                ", valeurHT=" + valeurHT +
                '}';
    }
}
