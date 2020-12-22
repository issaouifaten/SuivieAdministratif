package com.example.suivieadministratif.ui.statistique_rapport_activite.article;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleDLC extends AppCompatActivity {
    String user, password, base, ip;
    String CodeSociete, NomUtilisateur ;
    String   CodeDepot = "", LibelleDepot = "";

    ConnectionClass connectionClass;
    GridView gridArticle;

    String querysearch="";

    ArrayList<String> datalibelle,datacode,dataLibelleFRS,datacodeFRS;
    Spinner  spindepot;
    ProgressBar progressBar;
    final Context co = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_d_l_c);
        SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Etat de Stock");


        /// CONNECTION BASE


        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        ///////////////////////////////////////

        connectionClass = new ConnectionClass();
        ////SESSION UTILISATEUR
        SharedPreferences prefe = getSharedPreferences(Param.PREF_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);
        CodeSociete = prefe.getString("CodeSociete", CodeSociete);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        gridArticle=(GridView)findViewById(R.id.grid_article);
        spindepot=(Spinner)findViewById(R.id.spin_depot);
        GetDataSpinner getDataSpinner=new  GetDataSpinner();
        getDataSpinner.execute("");
        spindepot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try{

                if(i!=0) {
                    CodeDepot = datacode.get(i);
                    LibelleDepot = datalibelle.get(i);


                    if (LibelleDepot.equals("Tout")) {

                        querysearch = "SELECT NumeroLot,CodeArticle, DateLimiteConsommation ,\n" +
                                "(select Designation from Article where Article.CodeArticle=DLCArticle.CodeArticle) as Designation  from DLCArticle\n" +
                                " " +
                                "order by CodeArticle,NumeroOrdre";

                    } else {
                        querysearch = "SELECT NumeroLot,CodeArticle, DateLimiteConsommation ,\n" +
                                "(select Designation from Article where Article.CodeArticle=DLCArticle.CodeArticle) as Designation  from DLCArticle\n" +
                                "WHERE CodeDepot='" + CodeDepot + "'\n" +
                                "order by CodeArticle,NumeroOrdre";
                    }


                    FillList fillList = new FillList();
                    fillList.execute("");
                }
                }catch (Exception e)
                {
                    Log.e("erreurspin",e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }




    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;

        ArrayList<Integer> list = new ArrayList<Integer>();
        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            list.clear();

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

////NumeroLot,CodeArticle, DateLimiteConsommation ,Designation
            String[] from = {"NumeroLot", "DateLimiteConsommation", "Designation", "CodeArticle"};
            int[] views = {R.id.txt_code, R.id.txt_designation, R.id.txt_nom_representant, R.id.tx_num_piece, R.id.txt_total_ttc,R.id.txt_nom_rad};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_journal_vente_article, from,
                    views);


            final BaseAdapter baseAdapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return prolist.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }


                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(co);
                    convertView = layoutInflater.inflate(R.layout.item_article_dlc, null);
                    final TextView txt_code = (TextView) convertView.findViewById(R.id.txt_code_art);
                    final TextView txt_designation = (TextView) convertView.findViewById(R.id.txt_des_art);
                    final TextView txt_numero_lot = (TextView) convertView.findViewById(R.id.txt_numero_lot);
                    final TextView txt_date = (TextView) convertView.findViewById(R.id.txt_date);

                    final LinearLayout layout_article = (LinearLayout) convertView.findViewById(R.id.layout_article);
                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    String NumeroLot = (String) obj.get("NumeroLot");
                    String Designation  = (String) obj.get("Designation");
      ;            String CodeArticle = (String) obj.get("CodeArticle");
      ;            String DateLimiteConsommation = (String) obj.get("DateLimiteConsommation");

                    txt_numero_lot.setText(NumeroLot);
                    txt_code.setText(CodeArticle);
                    txt_designation.setText(Designation);
                    txt_date.setText(DateLimiteConsommation);


                    if (list.contains(position)) {
                        layout_article.setVisibility(View.VISIBLE);
                    } else {
                        layout_article.setVisibility(View.GONE);
                    }

                    return convertView;
                }
            };


            gridArticle.setAdapter(baseAdapter);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {




                    PreparedStatement ps = con.prepareStatement(querysearch);
                    Log.e("querysearch", querysearch);

                    ResultSet rs = ps.executeQuery();
                    z = "e";
                    String ancien = "";
                    int posi = 0;
                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroLot", rs.getString("NumeroLot"));
                        datanum.put("CodeArticle", rs.getString("CodeArticle"));
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        datanum.put("DateLimiteConsommation", df.format(rs.getDate("DateLimiteConsommation")));

                        datanum.put("Designation", rs.getString("Designation"));
                        String nom = rs.getString("CodeArticle").toUpperCase();
                        if (!ancien.equals(nom)) {
                            ancien = nom;

                            list.add(posi);
                        }
                        posi++;


                        prolist.add(datanum);


                        test = true;


                        z = "succees";
                    }


                }
            } catch (SQLException ex) {
                z = "tablelist" + ex.toString();
                Log.e("erreur", z);


            }
            catch (Exception ex) {
                z = "tablelist" + ex.toString();
                Log.e("erreur", z);


            }
            return z;
        }
    }




    public class GetDataSpinner extends AsyncTask<String, String, String> {
        String z = "  ";

        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        @Override
        protected void onPreExecute() {
            //  Log.e("frs", querylist);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //   Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();
            //   Toast.makeText(getApplicationContext(), r, Toast.LENGTH_SHORT).show();
            String[] array = datacode.toArray(new String[0]);


            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(),
                    R.layout.spinner, datalibelle);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spindepot.setAdapter(adapter);






        }
        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    PreparedStatement stmt;
                    datalibelle = new ArrayList<String>();
                    datacode = new ArrayList<String>();

                    String querydepot="select * from Depot where CodeNature=1";

                    stmt = con.prepareStatement(querydepot);
                    ResultSet rsss = stmt.executeQuery();
                    Log.e("spindepot", querydepot);
                    datalibelle.add("Rechercher Depot");
                    datacode.add("");
                    while (rsss.next()) {
                        String libelle= rsss.getString("Libelle");
                        datalibelle.add(libelle);
                        String CodeDepot= rsss.getString("CodeDepot");
                        datacode.add(CodeDepot);

                    }


                }
            } catch (SQLException ex) {

                z = "tablelist" + ex.toString();
                Log.e("erreur", z);
            } catch (Exception e) {
                z = "tablelist" + e.toString();
                Log.e("erreur", z);
            }
            return z;
        }
    }
}