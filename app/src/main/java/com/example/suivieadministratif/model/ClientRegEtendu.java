package com.example.suivieadministratif.model;

public class ClientRegEtendu {


  private    String  CodeClient ;
  private    String  RaisonSociale ;

  private   double  solde  ;

  public ClientRegEtendu(String codeClient, String raisonSociale) {
    CodeClient = codeClient;
    RaisonSociale = raisonSociale;
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

  public double getSolde() {
    return solde;
  }

  public void setSolde(double solde) {
    this.solde = solde;
  }

  @Override
  public String toString() {

    return "ClientRegEtendu{" +
            "CodeClient='" + CodeClient + '\'' +
            ", RaisonSociale='" + RaisonSociale + '\'' +
            '}';

  }
}
