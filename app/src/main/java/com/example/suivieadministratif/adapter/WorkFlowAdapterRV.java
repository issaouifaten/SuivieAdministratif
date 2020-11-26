package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.CaisseDepense;
import com.example.suivieadministratif.model.WorkFlow;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class WorkFlowAdapterRV extends RecyclerView.Adapter<WorkFlowAdapterRV.ViewHolder> {

    private final Activity activity;

    private final ArrayList<WorkFlow> listWorkFlow ;

    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    NumberFormat formatter = new DecimalFormat("0.000");


    public WorkFlowAdapterRV(Activity activity, ArrayList<WorkFlow> listWorkFlow) {
        this.activity = activity;
        this.listWorkFlow = listWorkFlow;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_flow, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final WorkFlow workFlow = listWorkFlow.get(position);


        holder.txt_libelle_work.setText(workFlow.getLibelle());
        holder.txt_cree_par .setText(workFlow.getCreerPar());
        holder.txt_date_creation.setText(df1.format(workFlow.getDateCreation()));


        if (workFlow.getVue1() ==0)
        {
            holder.ll_vue_1.setVisibility(View.GONE);
        }
        else {
            holder.ll_vue_1.setVisibility(View.VISIBLE);
        }


        if (workFlow.getVue2() ==0)
        {
            holder.ll_vue_2.setVisibility(View.GONE);
        }
        else {
            holder.ll_vue_2.setVisibility(View.VISIBLE);
        }



        if (workFlow.getVue3() ==0)
        {
            holder.ll_vue_3.setVisibility(View.GONE);
        }
        else {
            holder.ll_vue_3.setVisibility(View.VISIBLE);

        }


        /*
        holder.txt_libelle_work.setText(workFlow.getLibelle());
        holder.txt_nom_compte.setText(  caisse.getLibelleCompte()  );
        holder.txt_solde.setText(formatter.format(caisse.getSolde()) + "");
        */

    }

    @Override
    public int getItemCount() {
        Log.e("size", "" + listWorkFlow.size());
        return listWorkFlow.size();
    }

     public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView card_caisse;

         public LinearLayout   ll_vue_1 ;
         public TextView txt_user_conservner_1 ;
         public TextView txt_date_vue_1 ;

         public LinearLayout  ll_vue_2 ;
         public TextView txt_user_conservner_2 ;
         public TextView txt_date_vue_2 ;


         public LinearLayout  ll_vue_3;
         public TextView txt_user_conservner_3 ;
         public TextView txt_date_vue_3 ;



        public TextView txt_cree_par ;
        public TextView txt_date_creation;
         public TextView  txt_libelle_work ;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_libelle_work = (TextView)  itemView.findViewById(R.id.txt_libelle_work);

            ll_vue_1=  (LinearLayout)  itemView.findViewById(R.id.ll_vue_1);
            txt_user_conservner_1 = (TextView)  itemView.findViewById(R.id.txt_vue_user_1);
            txt_date_vue_1 = (TextView)  itemView.findViewById(R.id.txt_date_vue_1);


            ll_vue_2=  (LinearLayout)  itemView.findViewById(R.id.ll_vue_2);
            txt_user_conservner_2 = (TextView)  itemView.findViewById(R.id.txt_vue_user_2);
            txt_date_vue_2 = (TextView)  itemView.findViewById(R.id.txt_date_vue_2);


            ll_vue_3=  (LinearLayout)  itemView.findViewById(R.id.ll_vue_3);
            txt_user_conservner_3 = (TextView)  itemView.findViewById(R.id.txt_vue_user_3);
            txt_date_vue_3 = (TextView)  itemView.findViewById(R.id.txt_date_vue_3);



            txt_cree_par = (TextView) itemView.findViewById(R.id.txt_cree_par);
            txt_date_creation = (TextView) itemView.findViewById(R.id.txt_date_creation);

        }
    }
}
