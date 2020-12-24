package com.example.suivieadministratif.model;

public class Article {

    private  String  CodeArticle  ;
    private  String  DesignationArticle ;


    public Article(String codeArticle, String designationArticle) {
        CodeArticle = codeArticle;
        DesignationArticle = designationArticle;
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

    @Override
    public String toString() {
        return "Article{" +
                "CodeArticle='" + CodeArticle + '\'' +
                ", DesignationArticle='" + DesignationArticle + '\'' +
                '}';
    }
}
