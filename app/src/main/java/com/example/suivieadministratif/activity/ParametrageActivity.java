package com.example.suivieadministratif.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.param.Param;


public class ParametrageActivity extends AppCompatActivity {



    boolean st = false;

    Button btadd ;
    EditText ed_ip_local  , ed_ip_distant , edtpass, edtbase, edtuser ;


    String  ip_selected  ="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrage2);

        btadd = (Button) findViewById(R.id.add);

        final Switch bt_distant = (Switch) findViewById(R.id.bt_distant);

        ed_ip_local = (EditText) findViewById(R.id.ip_local);
        ed_ip_distant = (EditText) findViewById(R.id.ip_distant);

        edtpass = (EditText) findViewById(R.id.password);
        edtuser = (EditText) findViewById(R.id.user);
        edtbase = (EditText) findViewById(R.id.db);


        SharedPreferences prefs =   getSharedPreferences(Param.PEF_SERVER , Context.MODE_PRIVATE);


        bt_distant.setChecked(true);
        bt_distant.setText("Distant");
        //final SharedPreferences.Editor edt = prefs.edit();
        //edt.putBoolean("etatsql", false);

        if( prefs.contains("ip_distant") && prefs.contains("ip_local")&& prefs.contains("ip") &&  prefs.contains("base") ) {
            String _ip_distant = prefs.getString("ip_distant", "");
            String _ip_local = prefs.getString("ip_local", "");
            String _ip = prefs.getString("ip", "");
            String _base = prefs.getString("base", "");

            ed_ip_distant.setText(_ip_distant);
            ed_ip_local.setText(_ip_local);
            edtbase.setText(_base);

             if( _ip.contains("192.168."))
             {
                 bt_distant.setChecked(false);
                 bt_distant.setText("Local");
                 ed_ip_local.setTextColor(getResources().getColor(R.color.color_g_7));
                 ed_ip_distant.setTextColor(getResources().getColor(R.color.ripple_lite));
             }
             else {
                 bt_distant.setChecked(true);
                 bt_distant.setText("Distant");

                 ed_ip_local.setTextColor(getResources().getColor(R.color.ripple_lite));
                 ed_ip_distant.setTextColor(getResources().getColor(R.color.color_g_7));
             }

        }


          bt_distant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if (b==true)
                 {
                     bt_distant.setText("Distant");

                     ed_ip_local.setTextColor(getResources().getColor(R.color.ripple_lite));
                     ed_ip_distant.setTextColor(getResources().getColor(R.color.color_g_7));

                     ip_selected = ed_ip_distant.getText().toString() ;

                 }
                  else {

                     bt_distant.setText("Local");

                     ed_ip_local.setTextColor(getResources().getColor(R.color.color_g_7));
                     ed_ip_distant.setTextColor(getResources().getColor(R.color.ripple_lite));

                     ip_selected = ed_ip_local.getText().toString() ;
                 }

              }
          });

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs =  getSharedPreferences(Param.PEF_SERVER, Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = prefs.edit();
                edt.putBoolean("etatsql", true);
                edt.putString("user", edtuser.getText().toString());
                edt.putString("password", edtpass.getText().toString());
                edt.putString("base", edtbase.getText().toString());
                edt.putString("ip", ip_selected);
                edt.putString("ip_distant", ed_ip_distant.getText().toString());
                edt.putString("ip_local", ed_ip_local.getText().toString());

                edt.commit();
                Intent intent = new Intent(getApplicationContext(), SplachScreenActivity.class);
                startActivity(intent);

                finish();
            }
        });


    }
}