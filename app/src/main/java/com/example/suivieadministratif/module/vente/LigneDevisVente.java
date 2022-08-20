package com.example.suivieadministratif.module.vente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.LigneBonLivraisonVente;
import com.example.suivieadministratif.pdf.ImpresionDevis;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LigneDevisVente extends AppCompatActivity {
    String NumeroDevisVente = "";
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;


    ListView lv_ligne_piece;

    ProgressBar pb  ;

    ArrayList<LigneBonLivraisonVente>  listLigneDevis=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ligne_piece);
        Intent intent = getIntent();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(LigneDevisVente.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(LigneDevisVente.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }


        NumeroDevisVente = intent.getStringExtra("NumeroDevisVente");
        String DateCreation = intent.getStringExtra("DateCreation");
        final    String RaisonSociale = intent.getStringExtra("RaisonSociale");
        final String TotalTTC = intent.getStringExtra("TotalTTC");
        final    String Etat = intent.getStringExtra("Etat");


        Button btn_imp_devis  = (Button) findViewById(R.id.btn_imp_devis) ;

        btn_imp_devis.setVisibility(View.VISIBLE);
        TextView txt_num_bc = (TextView) findViewById(R.id.txt_num_piece);
        TextView txt_prix_ttc = (TextView) findViewById(R.id.txt_prix_ttc);
        TextView txt_raison_client = (TextView) findViewById(R.id.txt_raison_client);
        TextView txt_date_bc = (TextView) findViewById(R.id.txt_date_bc);
        txt_num_bc.setText(NumeroDevisVente);
        txt_prix_ttc.setText(TotalTTC);
        txt_raison_client.setText(RaisonSociale);
        txt_date_bc.setText(DateCreation);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Détail Devis");
        connectionClass = new ConnectionClass();
        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        lv_ligne_piece = (ListView) findViewById(R.id.lv_ligne_piece);
        pb= (ProgressBar) findViewById(R.id.pb)  ;

        FillList fillList = new FillList();
        fillList.execute("");



        btn_imp_devis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                createandDisplayPdf(NumeroDevisVente, RaisonSociale,TotalTTC,listLigneDevis);


            }
        });
    }

    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();


        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {


            String[] from = {"DesignationArticle", "CodeArticle", "NetHT", "TauxRemise", "MontantTTC", "Quantite", "MontantTTC"};
            int[] views = {R.id.txt_designation,R.id.txt_code_article  , R.id.txt_net_ht, R.id.txt_taux_remise, R.id.txt_mnt_ttc, R.id.txt_quantite, R.id.txt_prix_ttc};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_ligne_piece, from,
                    views);

            pb.setVisibility(View.INVISIBLE);
            lv_ligne_piece.setAdapter(ADA);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String queryTable = " select  CodeArticle,DesignationArticle, NetHT , TauxRemise  , MontantTTC  , convert(numeric(18,0),Quantite)as Quantite   from LigneDevisVente where NumeroDevisVente='" + NumeroDevisVente + "' ";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryDetailDevisVente", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {


                        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
                        instance.setMinimumFractionDigits(3);
                        instance.setMaximumFractionDigits(3);

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        datanum.put("DesignationArticle", rs.getString("DesignationArticle"));

                        datanum.put("NetHT", instance.format(rs.getDouble("NetHT")));
                        datanum.put("TauxRemise", rs.getInt("TauxRemise") + " %");
                        datanum.put("MontantTTC", instance.format(rs.getDouble("MontantTTC")));

                        datanum.put("Quantite", rs.getInt("Quantite") + "");

                        listLigneDevis.add(new LigneBonLivraisonVente(NumeroDevisVente ,rs.getString("CodeArticle") , rs.getString("DesignationArticle"), rs.getInt("Quantite") ,
                                rs.getDouble("NetHT") ,rs.getInt("TauxRemise") ,rs.getDouble("MontantTTC")));

                        prolist.add(datanum);


                        test = true;


                        z = "succees";
                    }


                }
            } catch (SQLException ex) {
                z = "tablelist" + ex.toString();
                Log.e("erreur", z);


            }
            return z;
        }
    }


    private File getOutputMediaFile() {
        Log.e("**", "getOutputMediaFile");
        // External sdcard location
      /*File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                IMAGE_DIRECTORY_NAME);



        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                Toast.makeText(LigneDevisVente.this,  "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory" ,Toast.LENGTH_LONG).show();


                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }*/

        // Create a media file name


     /*   Toast.makeText(LigneDevisVente.this,  mediaStorageDir.getPath() + File.separator  + "Devis" + ".pdf" ,Toast.LENGTH_LONG).show();

        File file   = new File(mediaStorageDir.getPath() + File.separator  + "Devis" + ".pdf");*/

        try {
            Toast.makeText(LigneDevisVente.this,  Environment.getExternalStorageDirectory()+"Devis.pdf" ,Toast.LENGTH_LONG).show();
            File file = new File(Environment.getExternalStorageDirectory(), "Devis.pdf");
            return file;
        }catch (Exception e)
        {
            Toast.makeText(LigneDevisVente.this,   "execp "+e.getMessage().toString() ,Toast.LENGTH_LONG).show();
            return null;
        }




    }



    public void createandDisplayPdf(String NumDevis , String RaisonSociale  , String ttc , ArrayList<LigneBonLivraisonVente> detailDevis) {

        Document doc = new Document();
        SimpleDateFormat  sdf= new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss") ;
        String  file_name = sdf.format(new Date())+"devis"+NumDevis.replace("/","_")+".pdf" ;
        try {
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/Dir";
           // Toast.makeText(this ,"path "+ path , Toast.LENGTH_LONG).show();


            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();


            File file = new File(dir, file_name);
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph("Devis N° "+NumDevis);
            Paragraph p2 = new Paragraph("Client "+RaisonSociale);
            Paragraph p3 = new Paragraph("TTC  "+ttc);
            Paragraph p4 = new Paragraph("   " );
          /*  Font paraFont= new Font(Font.NORMAL);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);*/

            //add paragraph to document
            doc.add(p1);
            doc.add(p2);
            doc.add(p3);
            doc.add(p4);

            for (LigneBonLivraisonVente  d : detailDevis)
            {
                Paragraph p22 = new Paragraph("* "+d.getCodeArticle()+" - "+d.getDesignationArticle()+" - "+d.getTauxRemise()+"% "+ "  ***  TTC = "+d.getMontantTTC() );
                doc.add(p22);

            }


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
            Toast.makeText(this ,"PDFCreator "+ "DocumentException:" + de , Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
            Toast.makeText(this ,"PDFCreator "+ "ioException:" + e , Toast.LENGTH_LONG).show();
        }
        finally {
            doc.close();
        }

        viewPdf(file_name, "Dir");
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + directory + "/" + file);
     //   Uri path = Uri.fromFile(pdfFile);

        Uri path =    FileProvider.getUriForFile(this  ,getApplicationContext().getPackageName() + ".provider", pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(LigneDevisVente.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
}
