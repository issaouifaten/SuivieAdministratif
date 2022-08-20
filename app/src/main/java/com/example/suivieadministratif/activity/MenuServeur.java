package com.example.suivieadministratif.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MenuServeur extends AppCompatActivity {
    ConnectionClass connectionClass;
    String CodeSociete, NomUtilisateur, CodeLivreur;
    final Context co = this;
    String user, password, base, ip;
    GridView gridSituation;

    GridView gridServeur;
    ProgressBar progressBar;

    public static Activity fa;
    String condition_distant = "";


    ProgressBar pb_test_conn;
    TextView txt_test_conn;
    LinearLayout ll_test_conn;

    TestConnection testConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_serveur);
        connectionClass = new ConnectionClass();

        fa = this;

        SharedPreferences prefe = getSharedPreferences("usersession", Context.MODE_PRIVATE);
        SharedPreferences.Editor edte = prefe.edit();
        NomUtilisateur = prefe.getString("NomUtilisateur", NomUtilisateur);

        final SharedPreferences pref = getSharedPreferences("usersessionsql", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        password = pref.getString("password", password);
        base = pref.getString("base", base);
        gridServeur = (GridView) findViewById(R.id.grid_serveur);

        pb_test_conn = (ProgressBar) findViewById(R.id.pb_test_conn);
        txt_test_conn = (TextView) findViewById(R.id.txt_test_conn);
        ll_test_conn = (LinearLayout) findViewById(R.id.ll_test_conn);

        ll_test_conn.setVisibility(View.GONE);


        testConnection = new TestConnection(ip, ll_test_conn, txt_test_conn, pb_test_conn );
        testConnection.execute();

        testConnexion(ip);


        final Switch bt_distant = (Switch) findViewById(R.id.bt_distant);

        bt_distant.setChecked(true);
        bt_distant.setText("Distant");
        condition_distant = " where Distant = 1";

        if (ip.contains("192.168.")) {
            condition_distant = " where Distant=0";
            bt_distant.setChecked(false);
            bt_distant.setText("Local");
        } else {
            bt_distant.setChecked(true);
            bt_distant.setText("Distant");

            condition_distant = " where Distant = 1";

        }


        bt_distant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    bt_distant.setText("Distant");
                    // The toggle is enabled

                    ip = pref.getString("ip_distant", ip);
                    testConnexion(ip);
                    condition_distant = " where Distant = 1";


                } else {

                    bt_distant.setText("Local");
                    ip = pref.getString("ip_local", ip);
                    // The toggle is disabled
                    testConnexion(ip);
                    condition_distant = " where Distant = 0";


                }
            }
        });


    }

    public void testConnexion(String ip) {

        //   AsyncTaskWithTimeout.this.get(30000, TimeUnit.MILLISECONDS );
        testConnection.cancel(false);

        testConnection = new TestConnection(ip, ll_test_conn, txt_test_conn, pb_test_conn);
        testConnection.execute();

        try {
            testConnection.get(3000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        //setting timeout thread for async task
        /*Thread thread1 = new Thread() {
            public void run() {
                try {
                    testConnection.get(5000, TimeUnit.MILLISECONDS);  //set time in milisecond(in this timeout is 30 seconds

                } catch (Exception e) {
                    testConnection.cancel(false);
                    ((Activity) MenuServeur.this).runOnUiThread(new Runnable() {
                        @SuppressLint("ShowToast")
                        public void run() {
                            Toast.makeText(MenuServeur.this, "Vérifier votre connexion Internet", Toast.LENGTH_LONG).show();


                        }

                    });
                }
            }
        };
        thread1.start();*/


    }


    public class FillList extends AsyncTask<String, String, String> {
        String z = "";
        Boolean test = false;


        List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
        float total_gloabl = 0;

        @Override
        protected void onPreExecute() {
            //  progressBar.setVisibility(View.VISIBLE);

            total_gloabl = 0;
            gridServeur.setVisibility(View.INVISIBLE);
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String queryTable = " select  *  from ListeSociete " + condition_distant;

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("query_list_soc", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";

                    while (rs.next()) {

                        if (NomUtilisateur.equals("NEJAH"))
                        {
                            if (rs.getString("NomBase").equals("MTD"))
                            {
                                Map<String, String> datanum = new HashMap<String, String>();
                                datanum.put("CodeSociete", rs.getString("CodeSociete"));
                                datanum.put("NomSociete", rs.getString("NomSociete"));
                                datanum.put("IP", rs.getString("IP"));
                                datanum.put("NomBase", rs.getString("NomBase"));
                                prolist.add(datanum);

                            }
                        }
                        else
                        {
                            Map<String, String> datanum = new HashMap<String, String>();
                            datanum.put("CodeSociete", rs.getString("CodeSociete"));
                            datanum.put("NomSociete", rs.getString("NomSociete"));
                            datanum.put("IP", rs.getString("IP"));
                            datanum.put("NomBase", rs.getString("NomBase"));
                            prolist.add(datanum);
                        }




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

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {
            //    progressBar.setVisibility(View.GONE);

            gridServeur.setVisibility(View.VISIBLE);
            String[] from = {"NomSociete", "CodeSociete", "IP", "NomBase"};
            int[] views = {R.id.txt_code, R.id.txt_designation, R.id.txt_nom_representant, R.id.tx_num_piece, R.id.txt_total_ttc, R.id.txt_nom_rad};
            final SimpleAdapter ADA = new SimpleAdapter(getApplicationContext(),
                    prolist, R.layout.item_chiffre_affaire_global, from, views);


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
                    convertView = layoutInflater.inflate(R.layout.item_serveur, null);

                    final ImageView img_societe = (ImageView) convertView.findViewById(R.id.img_societe);
                    final TextView txt_nomsociete = (TextView) convertView.findViewById(R.id.txt_nomsociete);
                    final CardView btn_login = (CardView) convertView.findViewById(R.id.btn_login);


                    final HashMap<String, Object> obj = (HashMap<String, Object>) ADA
                            .getItem(position);
                    final String NomSociete = (String) obj.get("NomSociete");
                    final String NomBase = (String) obj.get("NomBase");
                    final String IP = (String) obj.get("IP");


//  String[] from = {"CodeClient", "RaisonSociale" ,"TypeOperation","NumeroPiece","TotalTTC"};
//

                    txt_nomsociete.setText(NomSociete);

                    if (NomSociete.equals("CCM")) {
                        img_societe.setImageResource(R.drawable.ic_logo_ccm);
                    } else if (NomSociete.equals("GMT")) {
                        img_societe.setImageResource(R.drawable.ic_logo_gmt);
                    } else if (NomSociete.contains("I2S")) {
                        img_societe.setImageResource(R.drawable.i2s);
                    } else if (NomSociete.contains("CMVI")) {
                        img_societe.setImageResource(R.drawable.cmvi_logo);
                    } else if (NomSociete.contains("MTD")) {
                        img_societe.setImageResource(R.drawable.mtd_logo_transportatio);
                    } else if (NomSociete.contains("EPL")) {
                        img_societe.setImageResource(R.drawable.epl);
                    } else if (NomSociete.contains("TECHNO")) {
                        img_societe.setImageResource(R.drawable.techno);
                    }

                    else if (NomSociete.contains("DRIFER")) {
                        img_societe.setImageResource(R.drawable.ic_logo_driffer);
                    }


                    else if (NomSociete.contains("OLIVETECH")) {
                        img_societe.setImageResource(R.drawable.ic_logo_otivtech);
                    }


                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences pref = getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edt = pref.edit();
                            edt.putString("base", NomBase);
                            edt.putString("ip", IP);
                            edt.putString("NomSociete", NomSociete);

                            edt.commit();

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                        }
                    });


                    return convertView;
                }
            };


            gridServeur.setAdapter(baseAdapter);


        }


    }



    public class TestConnection extends AsyncTask<String, String, String> {

        String z = "";
        Boolean test = false;

        String ip;
        LinearLayout ll_test_conn;
        TextView txt_test_conn;
        ProgressBar pb_test_conn;


        public TestConnection(String ip, LinearLayout ll_test_conn, TextView txt_test_conn, ProgressBar pb_test_conn) {
            this.ip = ip;
            this.ll_test_conn = ll_test_conn;
            this.txt_test_conn = txt_test_conn;
            this.pb_test_conn = pb_test_conn;

            Log.e("test_CONN" ,  ip) ;
        }

        @Override
        protected void onPreExecute() {
            ll_test_conn.setVisibility(View.VISIBLE);
            pb_test_conn.setVisibility(View.VISIBLE);
            txt_test_conn.setText("Vérification Connexion en cours ...");

            gridServeur.setVisibility(View.INVISIBLE);
        }


        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN(ip, password, user, base);

                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {

                    String queryTable = " select  *  from ListeSociete";

                    PreparedStatement ps = con.prepareStatement(queryTable);
                    Log.e("query_list_soc", queryTable);

                    ResultSet rs = ps.executeQuery();
                    z = "e";
                    test = false;
                    while (rs.next()) {

                        Log.e("test_CONN"  , "result = true ") ;
                        test = true;

                    }


                }
            } catch (SQLException ex) {
                z = "tablelist" + ex.toString();
                Log.e("erreur", z);
            }
            return z;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String r) {

            pb_test_conn.setVisibility(View.INVISIBLE);
            if (test) {
                ll_test_conn.setVisibility(View.VISIBLE);

                txt_test_conn.setText("Connexion établie avec Succès");

                gridServeur.setVisibility(View.VISIBLE);

                FillList fillList = new FillList();
                fillList.execute("");


            } else {
                ll_test_conn.setVisibility(View.VISIBLE);
                txt_test_conn.setText("Vérifier votre connexion Internet");

                gridServeur.setVisibility(View.INVISIBLE);
            }


        }


        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);

            Log.e("test_CONN" , "onCancel  "+ test) ;

            if (test)
            {

            }
            else {

                pb_test_conn.setVisibility(View.INVISIBLE);
                txt_test_conn.setText("Vérifier votre connexion Internet");
            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menudeconnecter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences pref = getSharedPreferences(Param.PREF_USER, Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pref.edit();
            edt.putBoolean("etat", false);
            edt.commit();

            finish();
            Intent inte = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(inte);


            return true;
        }

        if (id == R.id.menu_param) {

            startActivity(new Intent(getApplicationContext(), ParametrageActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }
}
