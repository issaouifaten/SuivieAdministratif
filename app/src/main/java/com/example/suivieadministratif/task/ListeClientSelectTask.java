package com.example.suivieadministratif.task;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.ConnectionClass;
import com.example.suivieadministratif.adapter.ClientSelectAdapterRV;
import com.example.suivieadministratif.adapter.FournisseurSelectAdapterRV;
import com.example.suivieadministratif.model.Client;
import com.example.suivieadministratif.model.Fournisseur;
import com.example.suivieadministratif.param.Param;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ListeClientSelectTask extends AsyncTask<String, String, String> {

    String res;
    Activity activity;
    RecyclerView rv_list_client;
    ProgressBar pb_chargement;

    SearchView search_bar_client;

    ConnectionClass connectionClass;
    String user, password, base, ip;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat dtfSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList<Client> listClient = new ArrayList<>();

    double total_retenu = 0;


    public ListeClientSelectTask(Activity activity, RecyclerView rv_list_client, ProgressBar pb_chargement, SearchView search_bar_client) {
        this.activity = activity;
        this.rv_list_client = rv_list_client;
        this.pb_chargement = pb_chargement;
        this.search_bar_client = search_bar_client;
        SharedPreferences pref = activity.getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);

        user = pref.getString("user", user);
        ip = pref.getString("ip", ip);
        base = pref.getString("base", base);
        password = pref.getString("password", password);

        connectionClass = new ConnectionClass();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb_chargement.setVisibility(View.VISIBLE);
        total_retenu = 0;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Connection con = connectionClass.CONN(ip, password, user, base);       // Connect to database
            Log.e("con", "" + con);
            if (con == null) {
                res = "Check Your Internet Access!";
            } else {


                String query = " select CodeClient  , RaisonSociale    from   Client  ";

                Log.e("query_frns", query);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {

                    String CodeClient = rs.getString("CodeClient");
                    String RaisonSociale = rs.getString("RaisonSociale");


                    Client client = new Client(CodeClient, RaisonSociale, 0);
                    listClient.add(client);


                }
            }
            con.close();

        } catch (Exception ex) {

            res = ex.getMessage();
            Log.e("ERROR_listRDV", res.toString());

        }
        return null;

    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pb_chargement.setVisibility(View.INVISIBLE);

        ClientSelectAdapterRV adapterRV = new ClientSelectAdapterRV(activity, listClient);
        rv_list_client.setAdapter(adapterRV);


        search_bar_client.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!search_bar_client.isIconified()) {
                    search_bar_client.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                final ArrayList<Client> fitlerClientList = filterlistClient (listClient, query);


                ClientSelectAdapterRV adapterRV = new ClientSelectAdapterRV(activity, fitlerClientList);
                rv_list_client.setAdapter(adapterRV);




                return false;
            }
        });




    }




    private ArrayList<Client> filterlistClient (ArrayList<Client> listClient , String term) {

        term = term.toLowerCase();
        final ArrayList<Client> filetrlistClient  = new ArrayList<>();

        for (Client   c    : listClient) {
            final String txtRaisonSocial = c.getRaisonSociale().toLowerCase();

            if (txtRaisonSocial.contains(term)) {
                filetrlistClient.add(c);
            }
        }
        return filetrlistClient;

    }



}
