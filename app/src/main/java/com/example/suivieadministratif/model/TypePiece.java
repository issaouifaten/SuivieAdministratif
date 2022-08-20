package com.example.suivieadministratif.model;

import java.util.ArrayList;

public class TypePiece {

    private   String  CodeClient    ;
    private   int     orderAffichage   ;
    private   String  Libelle   ;

    private   double  Total_mnt  ;

    ArrayList <PieceRecouvrementEtendu>  listPieceRecouvEtendu ;

    public TypePiece() {
    }

    public TypePiece  (String codeClient, int orderAffichage, String libelle) {
        CodeClient = codeClient;
        this.orderAffichage = orderAffichage;
        Libelle = libelle;

    }

    public String getCodeClient() {
        return CodeClient;
    }

    public void setCodeClient(String codeClient) {
        CodeClient = codeClient;
    }

    public int getOrderAffichage() {
        return orderAffichage;
    }

    public void setOrderAffichage(int orderAffichage) {
        this.orderAffichage = orderAffichage;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }




    public ArrayList<PieceRecouvrementEtendu> getListPieceRecouvEtendu() {
        return listPieceRecouvEtendu;
    }

    public void setListPieceRecouvEtendu(ArrayList<PieceRecouvrementEtendu> listPieceRecouvEtendu) {
        this.listPieceRecouvEtendu = listPieceRecouvEtendu;
    }

    public double getTotal_mnt() {
        return Total_mnt;
    }

    public void setTotal_mnt(double total_mnt) {
        Total_mnt = total_mnt;
    }

    @Override
    public String toString() {
        return "TypePiece{" +
                "CodeClient='" + CodeClient + '\'' +
                ", orderAffichage=" + orderAffichage +
                ", Libelle='" + Libelle + '\'' +
                ", listPieceRecouvEtendu=" + listPieceRecouvEtendu +
                '}';
    }
}
