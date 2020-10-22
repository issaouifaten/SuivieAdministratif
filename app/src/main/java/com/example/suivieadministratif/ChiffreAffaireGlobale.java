package com.example.suivieadministratif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suivieadministratif.adapter.TotalCALV;
import com.example.suivieadministratif.model.ChiffreAffaireParSociete;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChiffreAffaireGlobale extends AppCompatActivity {


    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridSituation;
    TextView txt_datedebut, txt_datefin;
    String datedebut = "", datefin = "";
    DatePicker datePicker;
    GridView gridEtat;
    ProgressBar progressBar;
    ArrayList<String> data_CodeRespensable, data_NomRespensable;
    Spinner spinRespensable;
    String condition = "";


    FloatingActionButton fab_arrow;
    RelativeLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;

    ListView  list_tot_ca  ;

    FillList fillList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_globale);

        setTitle( "Chiffre d'Affaire Global");

        connectionClass = new ConnectionClass();
        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_datedebut = (TextView) findViewById(R.id.txt_date_debut);
        txt_datefin = (TextView) findViewById(R.id.txt_date_fin);

        gridEtat = (GridView) findViewById(R.id.grid_detail);

        list_tot_ca = (ListView)   findViewById(R.id.lv_list_tot_ca) ;

        CardView card_date_debut = (CardView) findViewById(R.id.card_date_debut);
        CardView card_date_fin = (CardView) findViewById(R.id.card_date_fin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        card_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(co);
                View px = li.inflate(R.layout.diagcalend, null);
                AlertDialog.Builder alt = new AlertDialog.Builder(co);
                alt.setIcon(R.drawable.i2s);
                alt.setView(px);
                alt.setTitle("date");
                datePicker = (DatePicker) px.findViewById(R.id.datedebut);
                alt.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {

                                Date d = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                                datedebut = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
                                        .format(d);

                                txt_datedebut.setText(datedebut);

                                  fillList = new FillList(ChiffreAffaireGlobale.this);
                                fillList.execute("");


                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();
            }
        });


        card_date_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(co);
                View px = li.inflate(R.layout.diagcalend, null);
                AlertDialog.Builder alt = new AlertDialog.Builder(co);
                alt.setIcon(R.drawable.i2s);
                alt.setView(px);
                alt.setTitle("date");
                datePicker = (DatePicker) px.findViewById(R.id.datedebut);
                alt.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface di, int i) {

                                Date d = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                                datefin = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
                                        .format(d);

                                txt_datefin.setText(datefin);


                                 fillList = new FillList(ChiffreAffaireGlobale.this);
                                fillList.execute("");
                            }
                        });

                AlertDialog dd = alt.create();
                dd.show();
            }
        });


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        datefin = sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -2);
        datedebut = sdf.format(calendar.getTime());
        txt_datedebut.setText(datedebut);
        txt_datefin.setText(datefin);

         fillList = new FillList(ChiffreAffaireGlobale.this);
        fillList.execute("");



        layoutBottomSheet = (RelativeLayout)  findViewById(R.id.bottom_sheet);
        fab_arrow = (FloatingActionButton)  findViewById(R.id.fab_arrow);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(false);


        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                        fab_arrow.setImageResource(R.drawable.ic_arrow_down);

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        // Toast.makeText(getActivity() , "Expand Sheet" ,Toast.LENGTH_LONG).show();
                        fab_arrow.setImageResource(R.drawable.ic_arrow_up);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        fillList.cancel(true) ;
    }

    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;

        int nb_ste = 1;
        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
        ArrayList<ChiffreAffaireParSociete> list_ca  = new ArrayList<>() ;


        Activity activity  ;

        public FillList(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            nb_ste = 1;

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            condition = "";


            String[] from = {"CodeClient", "RaisonSociale", "NomRepresentant", "NumeroPiece", "TotalTTC", "NomRAD", "NbSociete", "NomSociete"};
            int[] views = {R.id.txt_code, R.id.txt_designation, R.id.txt_nom_representant, R.id.tx_num_piece, R.id.txt_total_ttc, R.id.txt_nom_rad};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_chiffre_affaire_global, from,
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
                    convertView = layoutInflater.inflate(R.layout.item_chiffre_affaire_global_societe, null);
                    final TextView txt_code = (TextView) convertView.findViewById(R.id.txt_code);
                    final TextView txt_designation = (TextView) convertView.findViewById(R.id.txt_designation);
                    final TextView txt_nom_representant = (TextView) convertView.findViewById(R.id.txt_nom_representant);
                    final TextView tx_num_piece = (TextView) convertView.findViewById(R.id.tx_num_piece);
                    final TextView txt_total_ttc = (TextView) convertView.findViewById(R.id.txt_total_ttc);
                    final TextView txt_nom_rad = (TextView) convertView.findViewById(R.id.txt_nom_rad);
                    final TextView txt_nom_ste = (TextView) convertView.findViewById(R.id.txt_nom_ste);
                    final CardView card_situation = (CardView) convertView.findViewById(R.id.card_situation);

                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    String CodeClient = (String) obj.get("CodeClient");
                    String RaisonSociale = (String) obj.get("RaisonSociale");
                    String NomRepresentant = (String) obj.get("NomRepresentant");

                    String NumeroPiece = (String) obj.get("NumeroPiece");
                    String TotalTTC = (String) obj.get("TotalTTC");
                    String NomRAD = (String) obj.get("NomRAD");
                    String NbSociete = (String) obj.get("NbSociete");
                    String NomSociete = (String) obj.get("NomSociete");

//  String[] from = {"CodeClient", "RaisonSociale" ,"TypeOperation","NumeroPiece","TotalTTC"};
//

                    txt_code.setText(CodeClient);
                    txt_nom_ste.setText(NomSociete);

                    txt_nom_representant.setText(NomRepresentant);
                    txt_designation.setText(RaisonSociale);
                    txt_nom_rad.setText(NomRAD);

                    tx_num_piece.setText(NumeroPiece);
                    txt_total_ttc.setText(TotalTTC);
                    if( (Integer.parseInt(NbSociete)%2 ==0))
                    {
                        card_situation.setBackgroundColor(Color.parseColor("#ebf0fa"));
                    }else{
                        card_situation.setBackgroundColor(Color.parseColor("#ffddcc"));
                    }


//
//                    switch (NbSociete) {
//                        case "1":
//                            card_situation.setBackgroundColor(Color.parseColor("#ebf0fa"));
//                            break;
//                        case "2":
//                            card_situation.setBackgroundColor(Color.parseColor("#ffddcc"));
//                            break;
//                        case "3": card_situation.setBackgroundColor( Color.parseColor("#e6fff2"));   break;
//
//                        default:
//                            card_situation.setBackgroundColor(Color.parseColor("#f2f2f2"));
//                            break;
//
//
//                    }


                    return convertView;
                }
            };


            gridEtat.setAdapter(baseAdapter);


            TotalCALV  adapter  = new TotalCALV(activity  , list_ca) ;
            list_tot_ca.setAdapter(adapter);

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String queryListServeur = "select * from ListeSociete";

                    PreparedStatement ps_ = con.prepareStatement(queryListServeur);
                    Log.e("queryListServeur", queryListServeur);

                    ResultSet rs_list = ps_.executeQuery();
                    z = "e";

                    list_ca.clear();
                    double total_ca  = 0  ;
                    while (rs_list.next()) {
                        String ip_list = rs_list.getString("IP");
                        String base_list = rs_list.getString("NomBase");
                        final String nom_list = rs_list.getString("NomSociete");


                        Connection con_local = connectionClass.CONN(ip_list, password, user, base_list);

                        String  query_t_ca  = "select  SUM(TotalTTC )  as ChiffreAffaire\n" +
                                "from Vue_GlobalVente  where DateFactureVente IN('"+datedebut+"','"+datefin+"') " ;

                        Log.e("query_ca_global", query_t_ca);
                        PreparedStatement ps_ca_g = con_local.prepareStatement(query_t_ca);
                        ResultSet rs_ca_g = ps_ca_g.executeQuery();




                        while (rs_ca_g.next()) {


                            String  nom_soc  = nom_list ;
                            double  ca_t_soc  = rs_ca_g.getDouble("ChiffreAffaire") ;

                            total_ca = total_ca  +ca_t_soc ;

                            ChiffreAffaireParSociete caSoc  = new ChiffreAffaireParSociete(nom_soc ,ca_t_soc)  ;
                            list_ca.add(caSoc) ;

                        }



                        String queryTable = "select CodeClient,RaisonSociale,TotalTTC,NumeroFactureVente,\n" +
                                "(select Nom from Respensable where Respensable.CodeRespensable=Vue_GlobalVente.CodeRepresentant)\n" +
                                " as NomRepresentant ," +
                                " (select Nom from Respensable where Respensable.CodeRespensable=Vue_GlobalVente.CodeRespensableAdministration) as NomRAD  \n" +
                                " from Vue_GlobalVente  where DateFactureVente IN('" + datedebut + "','" + datefin + "') " + condition;


                        PreparedStatement ps = con_local.prepareStatement(queryTable);
                        Log.e("query_ca_global", queryTable);

                        ResultSet rs = ps.executeQuery();
                        z = "e";


                        while (rs.next()) {

                            Map<String, String> datanum = new HashMap<String, String>();
                            datanum.put("CodeClient", rs.getString("CodeClient"));
                            datanum.put("RaisonSociale", rs.getString("RaisonSociale"));
                            datanum.put("NumeroPiece", rs.getString("NumeroFactureVente"));

                            datanum.put("TotalTTC", rs.getString("TotalTTC"));

                            datanum.put("NomRepresentant", rs.getString("NomRepresentant"));
                            datanum.put("NomRAD", rs.getString("NomRAD"));
                            datanum.put("NbSociete", nb_ste + "");
                            datanum.put("NomSociete", nom_list);


                            prolist.add(datanum);

                            test = true;
                            z = "succees";

                        }

                        nb_ste++;
                        //////////////////////////////


                    }

                    list_ca.add(new ChiffreAffaireParSociete("Total",total_ca)) ;
                }
            } catch (SQLException ex) {
                z = "tablelist" + ex.toString();
                Log.e("erreur", z);


            }
            return z;
        }
    }

}
