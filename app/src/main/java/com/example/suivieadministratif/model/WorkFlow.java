package com.example.suivieadministratif.model;

import java.util.Date;

public class WorkFlow {

    private int NumeroWorkflow;
    private String CodeEvenement;
    private String Libelle;
    private Date DateCreation;
    private String CreerPar;
    private String NumeroPiece;
    private String UtilisateurConserner1;
    private int Vue1;
    private Date DateDebut1;
    private Date DateFin1;
    private String UtilisateurConserner2;
    private int Vue2;
    private Date DateVue2;
    private Date DateDebut2;
    private Date DateFin2;
    private String UtilisateurConserner3;
    private int Vue3;
    private Date DateVue3;
    private Date DateDebut3;
    private Date DateFin3;
    private String UtilisateurValideur;
    private Date DateFin;
    private int Fini;


    public WorkFlow(int numeroWorkflow, String codeEvenement, String libelle, Date dateCreation, String creerPar, String numeroPiece, String utilisateurConserner1, int vue1, Date dateDebut1, Date dateFin1, String utilisateurConserner2, int vue2, Date dateVue2, Date dateDebut2, Date dateFin2, String utilisateurConserner3, int vue3, Date dateVue3, Date dateDebut3, Date dateFin3, String utilisateurValideur, Date dateFin, int fini) {
        NumeroWorkflow = numeroWorkflow;
        CodeEvenement = codeEvenement;
        Libelle = libelle;
        DateCreation = dateCreation;
        CreerPar = creerPar;
        NumeroPiece = numeroPiece;
        UtilisateurConserner1 = utilisateurConserner1;
        Vue1 = vue1;
        DateDebut1 = dateDebut1;
        DateFin1 = dateFin1;
        UtilisateurConserner2 = utilisateurConserner2;
        Vue2 = vue2;
        DateVue2 = dateVue2;
        DateDebut2 = dateDebut2;
        DateFin2 = dateFin2;
        UtilisateurConserner3 = utilisateurConserner3;
        Vue3 = vue3;
        DateVue3 = dateVue3;
        DateDebut3 = dateDebut3;
        DateFin3 = dateFin3;
        UtilisateurValideur = utilisateurValideur;
        DateFin = dateFin;
        Fini = fini;
    }


    public int getNumeroWorkflow() {
        return NumeroWorkflow;
    }

    public void setNumeroWorkflow(int numeroWorkflow) {
        NumeroWorkflow = numeroWorkflow;
    }

    public String getCodeEvenement() {
        return CodeEvenement;
    }

    public void setCodeEvenement(String codeEvenement) {
        CodeEvenement = codeEvenement;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public Date getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        DateCreation = dateCreation;
    }

    public String getCreerPar() {
        return CreerPar;
    }

    public void setCreerPar(String creerPar) {
        CreerPar = creerPar;
    }

    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public String getUtilisateurConserner1() {
        return UtilisateurConserner1;
    }

    public void setUtilisateurConserner1(String utilisateurConserner1) {
        UtilisateurConserner1 = utilisateurConserner1;
    }

    public int getVue1() {
        return Vue1;
    }

    public void setVue1(int vue1) {
        Vue1 = vue1;
    }

    public Date getDateDebut1() {
        return DateDebut1;
    }

    public void setDateDebut1(Date dateDebut1) {
        DateDebut1 = dateDebut1;
    }

    public Date getDateFin1() {
        return DateFin1;
    }

    public void setDateFin1(Date dateFin1) {
        DateFin1 = dateFin1;
    }

    public String getUtilisateurConserner2() {
        return UtilisateurConserner2;
    }

    public void setUtilisateurConserner2(String utilisateurConserner2) {
        UtilisateurConserner2 = utilisateurConserner2;
    }

    public int getVue2() {
        return Vue2;
    }

    public void setVue2(int vue2) {
        Vue2 = vue2;
    }

    public Date getDateVue2() {
        return DateVue2;
    }

    public void setDateVue2(Date dateVue2) {
        DateVue2 = dateVue2;
    }

    public Date getDateDebut2() {
        return DateDebut2;
    }

    public void setDateDebut2(Date dateDebut2) {
        DateDebut2 = dateDebut2;
    }

    public Date getDateFin2() {
        return DateFin2;
    }

    public void setDateFin2(Date dateFin2) {
        DateFin2 = dateFin2;
    }

    public String getUtilisateurConserner3() {
        return UtilisateurConserner3;
    }

    public void setUtilisateurConserner3(String utilisateurConserner3) {
        UtilisateurConserner3 = utilisateurConserner3;
    }

    public int getVue3() {
        return Vue3;
    }

    public void setVue3(int vue3) {
        Vue3 = vue3;
    }

    public Date getDateVue3() {
        return DateVue3;
    }

    public void setDateVue3(Date dateVue3) {
        DateVue3 = dateVue3;
    }

    public Date getDateDebut3() {
        return DateDebut3;
    }

    public void setDateDebut3(Date dateDebut3) {
        DateDebut3 = dateDebut3;
    }

    public Date getDateFin3() {
        return DateFin3;
    }

    public void setDateFin3(Date dateFin3) {
        DateFin3 = dateFin3;
    }

    public String getUtilisateurValideur() {
        return UtilisateurValideur;
    }

    public void setUtilisateurValideur(String utilisateurValideur) {
        UtilisateurValideur = utilisateurValideur;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date dateFin) {
        DateFin = dateFin;
    }

    public int getFini() {
        return Fini;
    }

    public void setFini(int fini) {
        Fini = fini;
    }

    @Override
    public String toString() {
        return "WorkFlow{" +
                "NumeroWorkflow='" + NumeroWorkflow + '\'' +
                ", CodeEvenement='" + CodeEvenement + '\'' +
                ", Libelle='" + Libelle + '\'' +
                ", DateCreation=" + DateCreation +
                ", CreerPar='" + CreerPar + '\'' +
                ", NumeroPiece='" + NumeroPiece + '\'' +
                ", UtilisateurConserner1='" + UtilisateurConserner1 + '\'' +
                ", Vue1=" + Vue1 +
                ", DateDebut1=" + DateDebut1 +
                ", DateFin1=" + DateFin1 +
                ", UtilisateurConserner2='" + UtilisateurConserner2 + '\'' +
                ", Vue2=" + Vue2 +
                ", DateVue2=" + DateVue2 +
                ", DateDebut2=" + DateDebut2 +
                ", DateFin2=" + DateFin2 +
                ", UtilisateurConserner3='" + UtilisateurConserner3 + '\'' +
                ", Vue3=" + Vue3 +
                ", DateVue3=" + DateVue3 +
                ", DateDebut3=" + DateDebut3 +
                ", DateFin3=" + DateFin3 +
                ", UtilisateurValideur='" + UtilisateurValideur + '\'' +
                ", DateFin=" + DateFin +
                ", Fini=" + Fini +
                '}';
    }
}

/**
 * select NumeroWorkflow ,WorkFlow.CodeEvenement ,
 * EvenementWorkflow.Libelle ,  DateCreation ,CreerPar ,NumeroPiece ,
 * WorkFlow. UtilisateurConserner1 ,Vue1  ,DateDebut1 , DateFin1 ,
 * WorkFlow. UtilisateurConserner2, Vue2 , DateVue2 , DateDebut2 ,DateFin2 ,
 * WorkFlow.UtilisateurConserner3 ,Vue3 ,DateVue3 ,DateDebut3 , DateFin3 ,
 * UtilisateurValideur , DateFin , Fini
 * from  WorkFlow
 * left  join  EvenementWorkflow on  EvenementWorkflow.CodeEvenement =  WorkFlow.CodeEvenement
 **/

