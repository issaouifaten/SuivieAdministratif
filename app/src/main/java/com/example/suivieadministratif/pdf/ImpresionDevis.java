package com.example.suivieadministratif.pdf;

import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImpresionDevis {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);

    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.BOLD);

    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);


    public void executePDF(String FILE , String nom, String prenom, String cin, String grade , String cause, String lang_voulu ) throws Exception  {

        try {




            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                    Font.BOLD);

            Log.e("pdf","execute")  ;
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();


            addDate(document  , new Date());
            //addTitlePage(document ,font , typeDemande);
            addText(document ,nom ,prenom  , cin , grade , cause, lang_voulu );





            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("exeption",""+e.getMessage())  ;
        }
    }

    private static void addDate(Document document  , Date dateContrat) throws DocumentException {

        // format date
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyy");

        // Ecriture de date
        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_RIGHT);
        addEmptyLine(preface, 3);
        preface.add(new Paragraph("Sfax le " + dt1.format(dateContrat),
                smallBold));
        document.add(preface);

    }

    private static void addTitlePage(Document document  , Font font , String typeDemande)
            throws DocumentException {

        Paragraph preface = new Paragraph();

        preface.setAlignment(Element.ALIGN_CENTER);

        addEmptyLine(preface, 1);
        preface.add(new Paragraph(typeDemande, catFont));


        preface.setAlignment(Element.ALIGN_CENTER);

        addEmptyLine(preface, 2);
        //preface.add(new Paragraph(client, catFont));

        document.add(preface);
        // Start a new page
        // document.newPage();
    }

    private static void addText(Document document, String nom, String prenom, String cin, String grade, String cause, String lang_voulu) throws DocumentException {

        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(paragraph, 2);
        paragraph.add(new Paragraph(" Demande d'attestation de travail ",catFont) );

        addEmptyLine(paragraph, 1);
        //catFont    en gras
        paragraph.add(new Phrase( "Nom : ", catFont));  paragraph.add(new Phrase( nom));

        addEmptyLine(paragraph, 1);

        paragraph.add(new Phrase( "Prenom : ", catFont));  paragraph.add(new Phrase( prenom));

        addEmptyLine(paragraph, 1);

        paragraph.add(new Phrase( "CIN : ", catFont));  paragraph.add(new Phrase( cin));


        addEmptyLine(paragraph, 1);



        paragraph.add(new Phrase( "Grade : ", catFont));  paragraph.add(new Phrase(  grade));



        addEmptyLine(paragraph, 1);

        paragraph.add(new Phrase( "Cause : ", catFont));  paragraph.add(new Phrase(  cause));

        addEmptyLine(paragraph, 1);

        paragraph.add(new Phrase( "Langue Voulue : ", catFont));  paragraph.add(new Phrase(  lang_voulu));

        addEmptyLine(paragraph, 13);
        addTable(document ,paragraph  ,catFont  , "Avis Administration " )  ;

        document.add(paragraph);
    }







    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }



    public static void addTable(Document document, Paragraph paragraph  , Font font, String Signature) throws DocumentException {
        PdfPTable table = new PdfPTable(1);

        //	Paragraph preface = new Paragraph();
        table.setWidthPercentage(50);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell c1 = new PdfPCell();
        c1.setColspan(1);
        c1.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        Paragraph paragraph1 = new Paragraph();
        paragraph1.setAlignment(Element.ALIGN_CENTER);
        paragraph1.add(new Phrase(Signature, font));
        addEmptyLine(paragraph1, 5);
        c1.addElement(paragraph1);

        // linge 1

        table.addCell(c1);

        paragraph.add(table);

        //document.add(preface);

    }
}


