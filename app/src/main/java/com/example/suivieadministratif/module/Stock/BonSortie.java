package com.example.suivieadministratif.module.Stock;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.activity.HomeActivity;
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

public class BonSortie extends AppCompatActivity {

    ListView lv_list ;
    ProgressBar progressBar;
    SearchView search_bar_client;



    final Context co = this;
    String user, password, base, ip;

    public static TextView txt_tot_ttc ,txt_tot_ht , txt_tot_tva ;


    String condition="";


    int id_DatePickerDialog = 0;
    Date currentDate = new Date();
    public static int year_x1, month_x1, day_x1;
    public static int year_x2, month_x2, day_x2;

    public static Date date_debut = null;
    public static Date date_fin = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("00");
    public TextView txt_date_debut, txt_date_fin;



    ConnectionClass connectionClass;
    String  NomUtilisateur ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_stock);

        //sql session
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        String NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete + " : Bon Sortie");
        connectionClass = new ConnectionClass();


        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);

        txt_tot_ht = (TextView) findViewById(R.id.txt_tot_ht);
        txt_tot_tva = (TextView) findViewById(R.id.txt_total_tva);
        txt_tot_ttc = (TextView) findViewById(R.id.txt_total_ttc);


        txt_date_debut = findViewById(R.id.txt_date_debut);
        txt_date_fin = findViewById(R.id.txt_date_fin);


        lv_list = (ListView) findViewById(R.id.lv_list);
        progressBar = (ProgressBar) findViewById(R.id.pb_bc);


        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(currentDate);
        //cal1.add(Calendar.MONTH, -1);
        year_x1 = cal1.get(Calendar.YEAR);
        month_x1 = cal1.get(Calendar.MONTH);
        day_x1 = 1;


        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);

        year_x2 = cal2.get(Calendar.YEAR);
        month_x2 = cal2.get(Calendar.MONTH);
        day_x2 = cal2.get(Calendar.DAY_OF_MONTH);


        DecimalFormat df_month = new DecimalFormat("00");
        DecimalFormat df_year = new DecimalFormat("0000");

        Log.e("date_debut ", "01/" + df_month.format(cal1.get(Calendar.MONTH) + 1) + "/" + df_year.format(cal1.get(Calendar.YEAR)));
        String _date_du = "01/" + df_month.format(cal1.get(Calendar.MONTH) + 1) + "/" + df_year.format(cal1.get(Calendar.YEAR));

        try {
            date_debut = sdf.parse(_date_du);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txt_date_debut.setText(_date_du);

        date_fin = cal2.getTime();
        String _date_au = sdf.format(cal2.getTime());
        txt_date_fin.setText(_date_au);


        search_bar_client=(SearchView)findViewById(R.id.search_bar_client) ;
        search_bar_client. setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_client.isIconified()) {
                    search_bar_client.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if( s.length() > 0) {
                    condition = " and( NumeroBonSortie like'%" + s + "%'  or  Depot.Libelle  like'%" + s + "%' ) ";

                }else{
                    condition="";
                }

                FillList fillList = new  FillList();
                fillList.execute("");

                return false;

            }
        });



         FillList fillList = new  FillList();
        fillList.execute("");

        txt_date_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_DatePickerDialog = 0;
                Log.e("month_x1", "On picker  : " + month_x1);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BonSortie.this, new DatePickerDialog.OnDateSetListener() {
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
                                date_debut = sdf.parse(_date_du);
                                date_fin = sdf.parse(_date_au);


                                FillList fillList = new  FillList();
                                fillList.execute("");


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

                DatePickerDialog datePickerDialog = new DatePickerDialog(BonSortie.this, new DatePickerDialog.OnDateSetListener() {
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
                                date_debut = sdf.parse(_date_du);
                                date_fin = sdf.parse(_date_au);

                                FillList fillList = new  FillList();
                                fillList.execute("");

                            } catch (Exception e) {
                                Log.e("Exception --", " " + e.getMessage());
                            }

                        }
                    }
                }, year_x2, month_x2, day_x2);
                datePickerDialog.show();
            }
        });





        NavigationView nav_menu=findViewById(R.id.nav_view);
        View root = nav_menu.getHeaderView(0);

        CardView btn_etat_de_stock = (CardView) root.findViewById(R.id.btn_etat_de_stock) ;
        btn_etat_de_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), EtatDeStockActivity.class);
                startActivity(intent2);
            }
        });
        //btn_bon_entree
        CardView  btn_bon_entree = (CardView) root.findViewById(R.id.btn_bon_entree) ;
        btn_bon_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), BonEntre.class);
                startActivity(intent2);
            }
        });
        //btn_bonsortie_de_stock
        CardView  btn_bonsortie_de_stock = (CardView) root.findViewById(R.id.btn_bonsortie_de_stock) ;
        btn_bonsortie_de_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), BonSortie.class);
                startActivity(intent2);
            }
        });
        //btn_passation_bon_transfert

        CardView  btn_passation_bon_transfert = (CardView) root.findViewById(R.id.btn_passation_bon_transfert) ;
        btn_passation_bon_transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), BonTransfertStock.class);
                startActivity(intent2);
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
//btn_bon_redressement
        CardView   btn_bon_redressement= (CardView) root.findViewById(R.id.btn_bon_redressement );
        btn_bon_redressement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBonRedressement = new Intent(getApplicationContext() , BonRedressement.class) ;
                startActivity(toBonRedressement);

            }
        });

    }

    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();

        double total_ttc= 0;
        double total_tva = 0 ;
        double total_net_ht =0 ;



        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);


        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

            // NumeroDevisVente,DateCreation,NomUtilisateur,CodeDepot,Depot,TotalTTC,Etat.Libelle as Etat
            String[] from = {"NumeroBonSortie", "DateCreation",   "Depot", "TotalHT", "TotalTVA", "TotalTTC",  "Etat","NomUtilisateur"};
            int[] views = {R.id.txt_num_piece, R.id.txt_date_bc, R.id.txt_raison_client , R.id.txt_prix_net_ht, R.id.txt_prix_tva,  R.id.txt_prix_ttc,  R.id.txt_libelle_etat ,R.id.txt_etablie_par };
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_etat_entete, from, views);

            final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
            instance.setMinimumFractionDigits(3);
            instance.setMaximumFractionDigits(3);


            txt_tot_ttc.setText( instance.format(total_ttc));
            txt_tot_ht.setText( instance.format(total_net_ht));
            txt_tot_tva.setText( instance.format(total_tva));



            lv_list.setAdapter(ADA);
            lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);

                    String  NumeroBonSortie = (String) obj.get("NumeroBonSortie");
                    String  DateCreation = (String) obj.get("DateCreation");
                    String  Depot = (String) obj.get("Depot");
                    String  Etat = (String) obj.get("Etat");
                    String  TotalTTC = (String) obj.get("TotalTTC");
                    Intent intent=new Intent(getApplicationContext(), DetailLigneBonSortie.class);
                    intent.putExtra("NumeroBonSortie",NumeroBonSortie);
                    intent.putExtra("DateCreation",DateCreation);
                    intent.putExtra("Depot",Depot);
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


                    String queryTable = "select NumeroBonSortie,Depot.Libelle as Depot ,BonSortie.CodeDepot, TotalHT  , TotalTVA  ,TotalTTC ,DateCreation  as Etat,NomUtilisateur\n" +
                            "from BonSortie \n" +
                            "inner join Depot on Depot.CodeDepot=BonSortie.CodeDepot   " +
                            "where DateCreation between '"+sdf.format(date_debut)+"' and '"+sdf.format(date_fin)+"'\n" +condition+
                            "order by DateCreation desc";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryDevisVente", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {
                        final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
                        instance.setMinimumFractionDigits(3);
                        instance.setMaximumFractionDigits(3);


                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("NumeroBonSortie", rs.getString("NumeroBonSortie"));
                        datanum.put("NomUtilisateur", rs.getString("NomUtilisateur"));
                        datanum.put("CodeDepot", rs.getString("CodeDepot"));
                        datanum.put("Depot", rs.getString("Depot"));
                        datanum.put("TotalHT",instance.format(rs.getDouble("TotalHT")) );
                        datanum.put("TotalTVA", instance.format(rs.getDouble("TotalTVA")));
                        datanum.put("TotalTTC", instance.format(rs.getDouble("TotalTTC")));
                        datanum.put("Etat", rs.getString("Etat"));

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


                        total_ttc+=rs.getDouble("TotalTTC");
                        total_tva+=rs.getDouble("TotalTVA");
                        total_net_ht+=rs.getDouble("TotalHT");

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
