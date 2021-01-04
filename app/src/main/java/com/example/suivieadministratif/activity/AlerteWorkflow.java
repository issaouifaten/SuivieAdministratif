package com.example.suivieadministratif.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.ui.menu.MenuAchatFragment;
import com.example.suivieadministratif.ui.menu.MenuCaisseFragment;
import com.example.suivieadministratif.ui.menu.MenuEtatDeStockFragment;
import com.example.suivieadministratif.ui.menu.MenuVenteFragment;
import com.example.suivieadministratif.ui.menu.StatistiqueMenuActivity;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Stock.Etat_Stock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivieadministratif.R;

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

public class AlerteWorkflow extends AppCompatActivity {
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridSituation;
    TextView txt_title;
    String datedebut = "", datefin = "";
    DatePicker datePicker;
    GridView gridEtat;
    ProgressBar progressBar;
    ArrayList<String> data_CodeRespensable, data_NomRespensable;
    Spinner spinRespensable;
    String condition = "";
    Boolean toggle=true;
   String NomSociete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerte_workflow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
          txt_title = (TextView) findViewById(R.id.txt_title);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
       NomSociete = pref.getString("NomSociete", "");
        setTitle(NomSociete  );
//        NavigationView nav_menu=findViewById(R.id.nav_view);
//        View headerView = nav_menu.getHeaderView(0);
//


//
//        CardView btn_statistique = (CardView)   headerView.findViewById(R.id.btn_statistique) ;
//        btn_statistique.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), StatistiqueMenuActivity.class));
//
//
//            }
//        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setTitle(NomSociete  );
//                String t="Liste Workflow";
//                if(toggle) {
//                    FillListWorkflow fillListWorkflow = new FillListWorkflow();
//                    fillListWorkflow.execute("");
//                    t="Liste Workflow";
//                }else{
//                    FillList fillList = new FillList();
//                    fillList.execute("");
//                    t="Liste Alerte";
//                }
//                toggle=!toggle;
//
//                Snackbar.make(view, t, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//
//            }
//        });



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
        gridEtat = (GridView) findViewById(R.id.grid_list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);


        RadioButton bt_workflow=(RadioButton)findViewById(R.id.btn_workflow) ;
        RadioButton bt_alerte=(RadioButton)findViewById(R.id.bt_alerte) ;
        bt_alerte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {

                    FillList fillList=new  FillList();
                    fillList.execute();

                }
            }
        });

        bt_workflow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {

                    FillListWorkflow fillList=new  FillListWorkflow();
                    fillList.execute();

                }
            }
        });


        FillList fillList=new  FillList();
        fillList.execute();





    }


    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            txt_title.setText(NomSociete+" : Alerte");

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "" + prolist.size(), Toast.LENGTH_SHORT).show();

            String[] from = {"Module", "CodeAlerte", "NomAlerte", "CodePiece", "DateCreation", "UtilisateurCreateur"};
            int[] views = {R.id.txt_module, R.id.txt_nom_alerte, R.id.txt_date_creation, R.id.txt_cree_par, R.id.txt_code_piece};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_alerte, from,
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
                    convertView = layoutInflater.inflate(R.layout.item_alerte, null);
                    final TextView txt_module = (TextView) convertView.findViewById(R.id.txt_module);
                    //  int[] views = {R.id.txt_module, R.id.txt_nom_alerte, R.id.txt_date_creation, R.id.txt_cree_par};
                    final TextView txt_nom_alerte = (TextView) convertView.findViewById(R.id.txt_nom_alerte);

                    final TextView tx_num_piece = (TextView) convertView.findViewById(R.id.txt_code_piece);

                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);


                    String Module = (String) obj.get("Module");
                    String CodeAlerte = (String) obj.get("CodeAlerte");
                    String NomAlerte = (String) obj.get("NomAlerte");


                    String Nb = (String) obj.get("Nb");
                    tx_num_piece.setText(Nb);
                    txt_module.setText(Module);
                    txt_nom_alerte.setText(NomAlerte);


                    Log.e("position", "" + position);

                    return convertView;
                }
            };


            gridEtat.setAdapter(baseAdapter);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {


                    String queryTable = " select DetailAlerteOnLigne.CodeAlerte,NomAlerte,  Module,count(DetailAlerteOnLigne.CodeAlerte)AS Nb\n" +
                            "from DetailAlerteOnLigne \n" +
                            "inner join AlerteOnLigne on AlerteOnLigne.CodeAlerte=DetailAlerteOnLigne.CodeAlerte\n" +
                            "where NomUtilisateur='"+NomUtilisateur+"'\n" +
                            "group by Module, DetailAlerteOnLigne.CodeAlerte,NomAlerte  \n" +
                            "order by Module";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("queryAlerteOnLigne", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();
                        datanum.put("CodeAlerte", rs.getString("CodeAlerte"));
                        datanum.put("NomAlerte", rs.getString("NomAlerte"));


                        datanum.put("Module", rs.getString("Module"));



                        datanum.put("Nb", rs.getString("Nb"));


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


    public class FillListWorkflow extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            txt_title.setText(NomSociete+" : Workflow");

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "" + prolist.size(), Toast.LENGTH_SHORT).show();

            String[] from = {"Libelle", "CodeAlerte", "NomAlerte", "CodePiece", "DateCreation", "UtilisateurCreateur"};
            int[] views = {R.id.txt_module, R.id.txt_nom_alerte, R.id.txt_date_creation, R.id.txt_cree_par, R.id.txt_code_piece};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_work_flow, from,
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
                    convertView = layoutInflater.inflate(R.layout.item_work_flow, null);


                    final TextView txt_date_creation = (TextView) convertView.findViewById(R.id.txt_date_creation);
                    final TextView txt_cree_par = (TextView) convertView.findViewById(R.id.txt_cree_par);
                    final TextView txt_libelle_work = (TextView) convertView.findViewById(R.id.txt_libelle_work);
                    final TextView txt_user_conservner_1 = (TextView) convertView.findViewById(R.id.txt_vue_user_1);
                    final TextView txt_date_vue_1 = (TextView) convertView.findViewById(R.id.txt_date_vue_1);
                    final TextView txt_user_conservner_2 = (TextView) convertView.findViewById(R.id.txt_vue_user_2);
                    final TextView txt_date_vue_2 = (TextView) convertView.findViewById(R.id.txt_date_vue_2);
                    final TextView txt_user_conservner_3 = (TextView) convertView.findViewById(R.id.txt_vue_user_3);
                    final TextView txt_date_vue_3 = (TextView) convertView.findViewById(R.id.txt_date_vue_3);


                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);

                      String Libelle = (String) obj.get("Libelle");
                     String NumeroPiece = (String) obj.get("CodePiece");
                    String DateCreation = (String) obj.get("DateCreation");
                    String UtilisateurCreateur = (String) obj.get("UtilisateurCreateur");
                 //   tx_num_piece.setText(NumeroPiece);
                    txt_libelle_work.setText(Libelle+":"+NumeroPiece);

                    txt_date_creation.setText(DateCreation);
                    txt_cree_par.setText(UtilisateurCreateur);



                    return convertView;
                }
            };


            gridEtat.setAdapter(baseAdapter);


        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String condition_user1="(WorkFlow.UtilisateurConserner1='"+NomUtilisateur+"' and Vue1=0  and  GETDATE() between DateDebut1 and DateFin1 )";
                    String condition_user2="(WorkFlow.UtilisateurConserner2='"+NomUtilisateur+"' and Vue2=0  and  GETDATE() between DateDebut2 and DateFin2 )";
                    String condition_user3="(WorkFlow.UtilisateurConserner3='"+NomUtilisateur+"' and Vue3=0  and  GETDATE() between DateDebut3 and DateFin3 )";


                    String query_work_flow = "  select NumeroWorkflow ,WorkFlow.CodeEvenement ,\n" +
                            " EvenementWorkflow.Libelle ,  DateCreation ,CreerPar ,NumeroPiece ,\n" +
                            " WorkFlow. UtilisateurConserner1 ,Vue1  ,DateDebut1 , DateFin1 ,\n" +
                            " WorkFlow. UtilisateurConserner2, Vue2 , DateVue2 , DateDebut2 ,DateFin2 ,\n" +
                            " WorkFlow.UtilisateurConserner3 ,Vue3 ,DateVue3 ,DateDebut3 , DateFin3 ,\n" +
                            " UtilisateurValideur , DateFin , Fini\n" +
                            " from  WorkFlow\n" +
                            " left  join  EvenementWorkflow on  EvenementWorkflow.CodeEvenement =  WorkFlow.CodeEvenement " +
                            "  where Fini=0 and ( "+condition_user1+" or "+condition_user2+" or "+condition_user3+"  )";


                    Log.e("query_work_flow",""+query_work_flow) ;
                    PreparedStatement ps = con.prepareStatement(query_work_flow);
                    ResultSet rs = ps.executeQuery();


                    while (rs.next()) {

                        Map<String, String> datanum = new HashMap<String, String>();


                        datanum.put("CodePiece", rs.getString("NumeroPiece"));
                        datanum.put("Libelle", rs.getString("Libelle"));


                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

                        datanum.put("DateCreation", df.format(rs.getDate("DateCreation")));
                        datanum.put("UtilisateurCreateur", rs.getString("CreerPar"));

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
