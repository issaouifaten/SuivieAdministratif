package com.example.suivieadministratif.model;

public class Client {

    private   String   CodeClient   ;
    private   String  RaisonSociale   ;
    private   int   nbrClick  ;

    public Client(String codeClient) {
        CodeClient = codeClient;
    }

    public Client(String codeClient, String raisonSociale) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
    }


    public Client(String codeClient, String raisonSociale, int nbrClick) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        this.nbrClick = nbrClick;
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

    public int getNbrClick() {
        return nbrClick;
    }

    public void setNbrClick(int nbrClick) {
        this.nbrClick = nbrClick;
    }

    @Override
    public String toString() {
        return "Client{" +
                "CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                '}';
    }
}
