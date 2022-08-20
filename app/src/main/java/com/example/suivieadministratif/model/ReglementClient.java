package com.example.suivieadministratif.model;

import java.util.Date;

public class ReglementClient {


    private String NumeroReglementClient;
    private Date dateReglementClient;
    private Date heureCreation ;
    private String RaisonSociale;
    private double  TotalPayement ;
    private String NomUtilisateur;
    private double  TotalRecu;


    public ReglementClient(String numeroReglementClient, Date dateReglementClient, Date heureCreation , String raisonSociale, double totalPayement, String nomUtilisateur) {
        NumeroReglementClient = numeroReglementClient;
        this.dateReglementClient = dateReglementClient;
        this.heureCreation=heureCreation;
        RaisonSociale = raisonSociale;
        TotalPayement = totalPayement;
        NomUtilisateur = nomUtilisateur;
    }

    public ReglementClient(String numeroReglementClient, Date dateReglementClient, Date heureCreation, String nomUtilisateur, double totalRecu) {
        NumeroReglementClient = numeroReglementClient;
        this.dateReglementClient = dateReglementClient;
        this.heureCreation = heureCreation;
        NomUtilisateur = nomUtilisateur;
        TotalRecu = totalRecu;
    }

    public double getTotalRecu() {
        return TotalRecu;
    }

    public void setTotalRecu(double totalRecu) {
        TotalRecu = totalRecu;
    }

    public String getNumeroReglementClient() {
        return NumeroReglementClient;
    }

    public void setNumeroReglementClient(String numeroReglementClient) {
        NumeroReglementClient = numeroReglementClient;
    }

    public Date getDateReglementClient() {
        return dateReglementClient;
    }

    public void setDateReglementClient(Date dateReglementClient) {
        this.dateReglementClient = dateReglementClient;
    }

    public String getRaisonSociale() {
        return RaisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }

    public double getTotalPayement() {
        return TotalPayement;
    }

    public void setTotalPayement(double totalPayement) {
        TotalPayement = totalPayement;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    public Date getHeureCreation() {
        return heureCreation;
    }

    public void setHeureCreation(Date heureCreation) {
        this.heureCreation = heureCreation;
    }

    @Override
    public String toString() {
        return "ReglementClient{" +
                "NumeroReglementClient='" + NumeroReglementClient + '\'' +
                ", dateReglementClient=" + dateReglementClient +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", TotalPayement=" + TotalPayement +
                ", NomUtilisateur='" + NomUtilisateur + '\'' +
                '}';
    }
}
