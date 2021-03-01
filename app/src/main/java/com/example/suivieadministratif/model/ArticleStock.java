package com.example.suivieadministratif.model;

public class ArticleStock {


    private  String  CodeArticle  ;
    private  String  DesignationArticle ;
    private  double PrixVenteHT ;
    private  double PrixVenteTTC ;
    private  double PrixVenteLivraisonTTC ;
    private  double  PrixAchatHT ;
    private  int    CodeTVA ;
    private  double TauxTVA ;
    private  int     QuantiteStock ;
    private  int     QuantiteLivre ;
    private  double  TauxRemise ;
    private  double   PrixVenteHTMargeArticle ;
    private  double   NoteArticle ;
    private  int     nbrClick ;
    private  String     NumBL ;

    public ArticleStock(String codeArticle, String designationArticle, int quantiteStock) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        QuantiteStock = quantiteStock;
    }

    public ArticleStock(String codeArticle, String designationArticle) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
    }

    public ArticleStock() {
    }

    public ArticleStock(String codeArticle, String designationArticle, double prixVenteHT, double prixVenteTTC, double  PrixAchatHT, int codeTVA, double tauxTVA, int QuantiteStock, double  TauxRemise , double PrixVenteHTMargeArticle , int nbrClick) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        PrixVenteHT = prixVenteHT;
        PrixVenteTTC = prixVenteTTC;

        this.PrixAchatHT=PrixAchatHT ;
        CodeTVA = codeTVA;
        TauxTVA = tauxTVA;
        this.QuantiteStock = QuantiteStock ;
        this.TauxRemise = TauxRemise ;
        this.PrixVenteHTMargeArticle=PrixVenteHTMargeArticle ;

        double  x  = PrixVenteHT  * (1- this.TauxRemise/100) ;
        this.NoteArticle = ( ( x - PrixAchatHT ) /x  ) * 100;
        this.nbrClick = nbrClick;
    }


    public ArticleStock(String codeArticle, String designationArticle, double prixVenteHT, double prixVenteTTC, double prixVenteLivraisonTTC , double  PrixAchatHT, int codeTVA, double tauxTVA, int QuantiteStock, int QuantiteLivre, double  TauxRemise , double PrixVenteHTMargeArticle , int nbrClick , String  NumBL ) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        PrixVenteHT = prixVenteHT;
        PrixVenteTTC = prixVenteTTC;
        this.PrixVenteLivraisonTTC=prixVenteLivraisonTTC;

        this.PrixAchatHT=PrixAchatHT ;
        CodeTVA = codeTVA;
        TauxTVA = tauxTVA;
        this.QuantiteStock = QuantiteStock ;
        this.QuantiteLivre = QuantiteLivre  ;
        this.TauxRemise = TauxRemise ;
        this.PrixVenteHTMargeArticle=PrixVenteHTMargeArticle ;

        double  x  = PrixVenteHT  * (1- this.TauxRemise/100) ;
        this.NoteArticle = ( ( x - PrixAchatHT ) /x  ) * 100;
        this.nbrClick = nbrClick;
        this.NumBL=NumBL ;
    }

    public ArticleStock(String codeArticle, String designationArticle, double prixVenteHT, double prixVenteTTC, double  PrixAchatHT, int codeTVA, double tauxTVA, int QuantiteStock , int nbrClick) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
        PrixVenteHT = prixVenteHT;
        PrixVenteTTC = prixVenteTTC;
        this.PrixAchatHT=PrixAchatHT ;
        CodeTVA = codeTVA;
        TauxTVA = tauxTVA;
        this.QuantiteStock = QuantiteStock ;
        this.nbrClick = nbrClick;
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

    public double getPrixVenteHT() {
        return PrixVenteHT;
    }

    public void setPrixVenteHT(double prixVenteHT) {
        PrixVenteHT = prixVenteHT;
    }

    public double getPrixVenteTTC() {
        return PrixVenteTTC;
    }

    public void setPrixVenteTTC(double prixVenteTTC) {
        PrixVenteTTC = prixVenteTTC;
    }

    public int getCodeTVA() {
        return CodeTVA;
    }

    public void setCodeTVA(int codeTVA) {
        CodeTVA = codeTVA;
    }

    public double getTauxTVA() {
        return TauxTVA;
    }

    public void setTauxTVA(double tauxTVA) {
        TauxTVA = tauxTVA;
    }

    public int getNbrClick() {
        return nbrClick;
    }

    public void setNbrClick(int nbrClick) {
        this.nbrClick = nbrClick;
    }

    public int getQuantiteStock() {
        return QuantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        QuantiteStock = quantiteStock;
    }

    public double getPrixAchatHT() {
        return PrixAchatHT;
    }

    public void setPrixAchatHT(double prixAchatHT) {
        PrixAchatHT = prixAchatHT;
    }


    public double getTauxRemise() {
        return TauxRemise;
    }

    public void setTauxRemise(double tauxRemise) {
        TauxRemise = tauxRemise;
    }


    public double getPrixVenteHTMargeArticle() {
        return PrixVenteHTMargeArticle;
    }

    public void setPrixVenteHTMargeArticle(double prixVenteHTMargeArticle) {
        PrixVenteHTMargeArticle = prixVenteHTMargeArticle;
    }

    public double getNoteArticle() {
        return NoteArticle;
    }

    public void setNoteArticle(double noteArticle) {
        NoteArticle = noteArticle;
    }


    public String getNumBL() {
        return NumBL;
    }

    public void setNumBL(String numBL) {
        NumBL = numBL;
    }

    public int getQuantiteLivre() {
        return QuantiteLivre;
    }

    public void setQuantiteLivre(int quantiteLivre) {
        QuantiteLivre = quantiteLivre;
    }

    public double getPrixVenteLivraisonTTC() {
        return PrixVenteLivraisonTTC;
    }

    public void setPrixVenteLivraisonTTC(double prixVenteLivraisonTTC) {
        PrixVenteLivraisonTTC = prixVenteLivraisonTTC;
    }

    @Override
    public String toString() {
        return "Article{" +
                "CodeArticle='" + CodeArticle + '\'' +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                ", PrixVenteHT=" + PrixVenteHT +
                ", PrixVenteTTC=" + PrixVenteTTC +
                ", PrixAchatHT=" + PrixAchatHT +
                ", CodeTVA=" + CodeTVA +
                ", TauxTVA=" + TauxTVA +
                ", QuantiteStock=" + QuantiteStock +
                ", TauxRemise=" + TauxRemise +
                ", PrixVenteHTMargeArticle=" + PrixVenteHTMargeArticle +
                ", NoteArticle=" + NoteArticle +
                ", nbrClick=" + nbrClick +
                '}';
    }
}
