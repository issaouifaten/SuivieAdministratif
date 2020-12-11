package com.example.suivieadministratif.ui.statistique_rapport_activite.article;

import androidx.appcompat.app.AppCompatActivity;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.TypeMouvementClick;
import com.example.suivieadministratif.task.ListTypeMvmntTask;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatCRMFragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

public class JournalActiviteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_activite);


        ListView lv_type_mvmnt = (ListView)   findViewById(R.id.lv_type_mvmnt) ;
        ProgressBar  pb  = (ProgressBar)   findViewById(R.id.pb) ;

        String _date_debut  = getIntent().getStringExtra("cle_date_debut" ) ;
        String _date_fin  = getIntent().getStringExtra("cle_date_fin" ) ;

        for (TypeMouvementClick t_mvmnt  : StatCRMFragment.listType)
        {
            if (t_mvmnt.getNbrClick() ==1)
            Log.e("t_mvmnt_journal",t_mvmnt.getLibelle()) ;
        }

      /*  Activity activity, String date_debut, String date_fin, ListView
        lv_type_mvmnt, ArrayList<TypeMouvementClick> listChoixTypeMvmnt, ProgressBar pb*/

        ListTypeMvmntTask  listTypeMvmntTask = new ListTypeMvmntTask(this , _date_debut ,_date_fin ,lv_type_mvmnt , StatCRMFragment.listType ,pb)  ;
        listTypeMvmntTask.execute() ;

    }
}