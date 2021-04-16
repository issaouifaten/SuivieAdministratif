package com.example.suivieadministratif.model;

import java.util.Date;

public class PortfeuilleClient {

    private  String    NumeroReglementClient  ;
    private Date Echeance ;
   private  String  CodeClient   ;
    private  String   RaisonSociale   ;
    private  String  NumeroPiece   ;
    private  String    Reference  ;
    private  double   Montant ;
    private  String    Banque  ;


    public PortfeuilleClient  (String numeroReglementClient, Date echeance, String codeClient, String raisonSociale, String  NumeroPiece   ,String reference, double montant, String banque)
    {
        NumeroReglementClient = numeroReglementClient;
        Echeance = echeance;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        this.NumeroPiece = NumeroPiece  ;
        Reference = reference;
        Montant = montant;
        Banque = banque;
    }


    public String getNumeroReglementClient() {
        return NumeroReglementClient;
    }

    public void setNumeroReglementClient(String numeroReglementClient) {
        NumeroReglementClient = numeroReglementClient;
    }

    public Date getEcheance() {
        return Echeance;
    }

    public void setEcheance(Date echeance) {
        Echeance = echeance;
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

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public double getMontant() {
        return Montant;
    }

    public void setMontant(double montant) {
        Montant = montant;
    }

    public String getBanque() {
        return Banque;
    }

    public void setBanque(String banque) {
        Banque = banque;
    }


    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    @Override
    public String toString() {
        return "PortfeuilleClient{" +
                "NumeroReglementClient='" + NumeroReglementClient + '\'' +
                ", Echeance=" + Echeance +
                ", CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", Reference='" + Reference + '\'' +
                ", Montant=" + Montant +
                ", Banque='" + Banque + '\'' +
                '}';
    }
}
