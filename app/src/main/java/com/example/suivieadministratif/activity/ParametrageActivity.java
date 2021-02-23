package com.example.suivieadministratif.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;


public class ParametrageActivity extends AppCompatActivity {



    boolean st = false;

    Button btadd ;
    EditText edtip, edtpass, edtbase, edtuser ;


    RadioButton rb_cnx_distante;
    RadioButton rb_cnx_i2s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrage2);

        btadd = (Button) findViewById(R.id.add);

        edtip = (EditText) findViewById(R.id.ip);
        edtpass = (EditText) findViewById(R.id.password);
        edtuser = (EditText) findViewById(R.id.user);
        edtbase = (EditText) findViewById(R.id.db);

        rb_cnx_distante = (RadioButton) findViewById(R.id.rb_wifi_distant);
        rb_cnx_i2s = (RadioButton) findViewById(R.id.rb_wifi_local);





        rb_cnx_distante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtip.setText("");
            }
        });


        rb_cnx_i2s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtip.setText("");
            }
        });

        SharedPreferences prefs =   getSharedPreferences(Param.PEF_SERVER , Context.MODE_PRIVATE);
        //final SharedPreferences.Editor edt = prefs.edit();
        //edt.putBoolean("etatsql", false);

        if( prefs.contains("ip") && prefs.contains("base") ) {
            String _ip = prefs.getString("ip", "");
            String _base = prefs.getString("base", "");

            edtip.setText(_ip);
            edtbase.setText(_base);


            if (_ip.equals("")) {
                rb_cnx_distante.setChecked(true);
            } else if (_ip.equals("")) {
                rb_cnx_i2s.setChecked(true);
            }


        }

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs =  getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = prefs.edit();
                edt.putBoolean("etatsql", true);
                edt.putString("user", edtuser.getText().toString());
                edt.putString("password", edtpass.getText().toString());
                edt.putString("base", edtbase.getText().toString());
                edt.putString("ip", edtip.getText().toString());

                edt.commit();
                Intent intent = new Intent(getApplicationContext(), SplachScreenActivity.class);
                startActivity(intent);

                finish();
            }
        });


    }
}