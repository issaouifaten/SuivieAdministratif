package com.example.suivieadministratif.module.vente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.HomeActivity;
import com.example.suivieadministratif.module.reglementClient.RapportEcheanceClientActivity;
import com.example.suivieadministratif.module.reglementClient.ReglementClientActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EtatDevisVente extends AppCompatActivity {

    GridView lv_list_historique_bc;
    ProgressBar progressBar;
    SearchView search_bar_client;



    final Context co = this;
    String user, password, base, ip;

    public static TextView txt_tot_commande;

    FloatingActionButton fab_arrow;
    RelativeLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;


    String queryTable="";


    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;



    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");
    public TextView txt_date_debut, txt_date_fin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_devis_vente);
        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " :Devis Vente");
        connectionClass = new ConnectionClass();

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        // SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_tot_commande = (TextView) findViewById(R.id.txt_tot_commande);
        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);


        lv_list_historique_bc = (GridView) findViewById(R.id.lv_list_historique_bc);
        progressBar = (ProgressBar) findViewById(R.id.pb_bc);



        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        //cal1.add(Calendar.MONTH, -1);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = 1 ;



        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);

        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);



        DecimalFormat  df_month = new DecimalFormat("00") ;
        DecimalFormat  df_year  = new DecimalFormat("0000") ;

        Log.e("date_debut ", "01/"+ df_month.format(cal1.get(Calendar.MONTH) +1)+"/"+df_year.format(cal1.get(Calendar.YEAR) ) ) ;
        String _date_du =  "01/"+ df_month.format(cal1.get(Calendar.MONTH) +1)+"/"+df_year.format(cal1.get(Calendar.YEAR) )  ;

        try {
            date_debut =  df .parse(_date_du);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txt_date_debut.setText(_date_du);

        date_fin = cal2.getTime();
        String _date_au = df.format(cal2.getTime());
        txt_date_fin.setText(_date_au);




        queryTable = "select NumeroDevisVente,DateCreation,NomUtilisateur,CodeClient,RaisonSociale,TotalTTC, TotalRemise ,TotalHT  , TotalTVA  , Etat.Libelle as Etat\n" +
                " from DevisVente\n" +
                " inner join Etat on Etat.NumeroEtat=DevisVente.NumeroEtat\n" +
                " where DateCreation between '"+df.format(date_debut)+"'and '"+df.format(date_fin)+"' order by NumeroDevisVente desc";

        updateData();

        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EtatDevisVente.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (id_DatePickerDialog == 0) {
                            year_x1 = year;
                            month_x1 = monthOfYear;
                            day_x1 = dayOfMonth;

                            txt_date_debut.setText("" + formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1);

                            String _date_du = formatter.format(day_x1) + "/" + formatter.format(month_x1 + 1) + "/" + year_x1 + " ";
                            String _date_au = txt_date_fin.getText().toString();


                            try {
                                date_debut = df.parse(_date_du);
                                date_fin = df.parse(_date_au);

                                queryTable = "select NumeroDevisVente,DateCreation,NomUtilisateur,CodeClient,RaisonSociale,TotalTTC, TotalRemise ,TotalHT  , TotalTVA  , Etat.Libelle as Etat\n" +
                                        " from DevisVente\n" +
                                        " inner join Etat on Etat.NumeroEtat=DevisVente.NumeroEtat\n" +
                                        " where DateCreation between '"+df.format(date_debut)+"'and '"+df.format(date_fin)+"' order by NumeroDevisVente desc";
                                updateData();


                            } catch (Exception e) {
                                Log.e("Exception--", " " + e.getMessage());
                            }
                        }
                    }
                }, year_x1, month_x1, day_x1);
                datePickerDialog.show();
            }
        });


        txt_date_fin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(EtatDevisVente.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (id_DatePickerDialog == 1) {

                            year_x2 = year;
                            month_x2 = monthOfYear;
                            day_x2 = dayOfMonth;

                            txt_date_fin.setText("" + formatter.format(day_x2) + "/" + formatter.format(month_x2 + 1) + "/" + year_x2);

                            String _date_au = "" + formatter.format(day_x2) + "/" + formatter.format(month_x2 + 1) + "/" + year_x2;
                            String _date_du = txt_date_debut.getText().toString();

                            try {
                                date_debut = df.parse(_date_du);
                                date_fin = df.parse(_date_au);

                                queryTable = "select NumeroDevisVente,DateCreation,NomUtilisateur,CodeClient,RaisonSociale,TotalTTC, TotalRemise ,TotalHT  , TotalTVA  , Etat.Libelle as Etat\n" +
                                        " from DevisVente\n" +
                                        " inner join Etat on Etat.NumeroEtat=DevisVente.NumeroEtat\n" +
                                        " where DateCreation between '"+df.format(date_debut)+"'and '"+df.format(date_fin)+"' order by NumeroDevisVente desc";

                                updateData() ;

                            } catch (Exception e) {
                                Log.e("Exception --", " " + e.getMessage());
                            }

                        }
                    }
                }, year_x2, month_x2, day_x2);
                datePickerDialog.show();
            }
        });






        EditText editText=(EditText)findViewById(R.id.search_bar_client) ;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                queryTable = "select NumeroDevisVente,DateCreation,NomUtilisateur ,CodeClient,RaisonSociale,TotalTTC,  TotalRemise ,TotalHT  , TotalTVA  , Etat.Libelle as Etat\n" +
                        " from DevisVente\n" +
                        " inner join Etat on Etat.NumeroEtat=DevisVente.NumeroEtat\n" +
                        " where DateCreation between '"+df.format(date_debut )+"'and '"+df.format(date_fin )+"' and ( NumeroDevisVente like'%"+s+"%' OR RaisonSociale LIKE'%"+s+"%')  order by NumeroDevisVente desc";


                updateData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });







        NavigationView nav_menu=findViewById(R.id.nav_view);
        View root = nav_menu.getHeaderView(0);


        CardView btn_devis_vente = (CardView) root.findViewById(R.id.btn_devis_vente)  ;
        btn_devis_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(), EtatDevisVente.class);
                startActivity(intent6);
            }
        });




        CardView   btn_bon_livraison = (CardView) root.findViewById(R.id.btn_bon_livraison)  ;
        btn_bon_livraison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(getApplicationContext(), EtatLivraisonActivity.class);
                startActivity(intent6);
            }
        });


        CardView btn_bon_retour = (CardView) root.findViewById(R.id.btn_bon_retour)  ;
        btn_bon_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(getApplicationContext(), EtatRetourActivity.class);
                startActivity(intent7);
            }
        });


        CardView  btn_bon_commande = (CardView) root.findViewById(R.id.btn_bon_commande) ;
        btn_bon_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), EtatCommande.class);
                startActivity(intent5);

            }
        });
        CardView  btn_facture_vente = (CardView) root.findViewById(R.id.btn_facture_vente) ;
        btn_facture_vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), EtatFactureVente.class);
                startActivity(intent5);

            }
        });

        CardView  btn_mvmnt_vente_service= (CardView) root.findViewById(R.id.btn_mvmnt_vente_service) ;
        btn_mvmnt_vente_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(getApplicationContext(), MouvementVenteServiceActivity.class);
                startActivity(intent5);
            }
        });


        CardView   btn_reglement_client = (CardView)  root.findViewById(R.id.btn_reg_client)  ;
        btn_reglement_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getApplicationContext() , ReglementClientActivity.class) ;
                startActivity(intent1);
            }
        });


        CardView   btn_echeance_client = (CardView)   root.findViewById(R.id.btn_echeance_Client);
        btn_echeance_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getApplicationContext() , RapportEcheanceClientActivity.class) ;
                startActivity(intent1);
            }
        });
        CardView   btn_home= (CardView) root.findViewById(R.id.btn_home );
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaisseRecette  = new Intent(getApplicationContext() , HomeActivity.class) ;
                startActivity(toCaisseRecette);

            }
        });





        layoutBottomSheet = (RelativeLayout) findViewById(R.id.bottom_sheet);
        fab_arrow = (FloatingActionButton) findViewById(R.id.fab_arrow);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(false);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        // Toast.makeText(getActivity() , "Close Sheet" ,Toast.LENGTH_LONG).show();
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


    public  void updateData()
    {
        FillList fillList = new  FillList();
        fillList.execute("");
    }

    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
        float total_devis=0;


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);


        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

// NumeroDevisVente,DateCreation,NomUtilisateur,CodeClient,RaisonSociale,TotalTTC,Etat.Libelle as Etat    ,  ,
            String[] from = {"NumeroDevisVente", "DateCreation",   "RaisonSociale","TotalTTC", "TotalRemise", "TotalHT", "TotalTVA","Etat"};
            int[] views = {R.id.txt_num_bc, R.id.txt_date_bc, R.id.txt_raison_client, R.id.txt_prix_ttc,  R.id.txt_remise, R.id.txt_prix_ht, R.id.txt_prix_tva , R.id.txt_libelle_etat};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_bon_commande, from,
                    views);





            final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
            instance.setMinimumFractionDigits(3);
            instance.setMaximumFractionDigits(3);
            txt_tot_commande.setText(instance.format(total_devis));





            lv_list_historique_bc.setAdapter(ADA);
            lv_list_historique_bc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);

                    String  NumeroDevisVente = (String) obj.get("NumeroDevisVente");
                    String  DateCreation = (String) obj.get("DateCreation");
                    String  RaisonSociale = (String) obj.get("RaisonSociale");
                    String  Etat = (String) obj.get("Etat");
                    String  TotalTTC = (String) obj.get("TotalTTC");
                    Intent intent=new Intent(getApplicationContext(),DetailLigneDevisVente.class);
                    intent.putExtra("NumeroDevisVente",NumeroDevisVente);
                    intent.putExtra("DateCreation",DateCreation);
                    intent.putExtra("RaisonSociale",RaisonSociale);
                    intent.putExtra("TotalTTC",TotalTTC);
                    intent.putExtra("Etat",Etat);
                    startActivity(intent);



                }
            });


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {



                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryDevisVente", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroDevisVente", rs.getString("NumeroDevisVente"));
                        datanum.put("NomUtilisateur", rs.getString("NomUtilisateur"));
                        datanum.put("CodeClient", rs.getString("CodeClient"));
                        datanum.put("RaisonSociale", rs.getString("RaisonSociale"));



                        datanum.put("TotalTTC", rs.getString("TotalTTC"));
                        datanum.put("TotalRemise", rs.getString("TotalRemise"));
                        datanum.put("TotalHT", rs.getString("TotalTTC"));
                        datanum.put("TotalTVA", rs.getString("TotalTVA"));


                        datanum.put("Etat", rs.getString("Etat"));

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                        datanum.put("DateCreation", df.format(rs.getDate("DateCreation")));
                        total_devis+=rs.getFloat("TotalTTC");
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


}
