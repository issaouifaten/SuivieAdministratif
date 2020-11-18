package com.example.suivieadministratif;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by faten on 04/03/2018.
 */
public class ConnectionClass {
    //  String ip = "192.168.1.26";
   // String ip = "196.179.246.166";
   String classs = "net.sourceforge.jtds.jdbc.Driver";
    // String db = "I2S_Delivery";
    //  String un = "sa";
    // String password = "ideal2s";

    @SuppressLint("NewApi")
    public Connection CONN(String ip, String password, String user , String db) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + user + ";password="
                    + password + ";";
            Log.e("ConnURL", ConnURL);

            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }





}
