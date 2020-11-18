package com.example.suivieadministratif.model;

import java.util.Date;

public class ReglementClient {


    private String NumeroReglementClient;
    private Date dateReglementClient;
    private String RaisonSociale;
    private double  TotalPayement ;
    private String NomUtilisateur;


    public ReglementClient(String numeroReglementClient, Date dateReglementClient, String raisonSociale, double totalPayement, String nomUtilisateur) {
        NumeroReglementClient = numeroReglementClient;
        this.dateReglementClient = dateReglementClient;
        RaisonSociale = raisonSociale;
        TotalPayement = totalPayement;
        NomUtilisateur = nomUtilisateur;
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
