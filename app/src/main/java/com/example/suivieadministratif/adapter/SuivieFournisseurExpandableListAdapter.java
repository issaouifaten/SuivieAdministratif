package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.suivieadministratif.R;

import com.example.suivieadministratif.model.EnteteRelationFournisseur;
import com.example.suivieadministratif.model.RelationFournisseur;
import com.example.suivieadministratif.ui.statistique_rapport_activite.Fournisseur.SuivieCommandeFrs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SuivieFournisseurExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<EnteteRelationFournisseur> listEnteteRelationFournisseur  ;
    public LayoutInflater inflater;

    SimpleDateFormat  sdf  = new SimpleDateFormat("dd/MM/yyyy") ;


    public SuivieFournisseurExpandableListAdapter(Activity activity, ArrayList<EnteteRelationFournisseur> listEnteteRelationFournisseur) {
        this.activity = activity;
        this.listEnteteRelationFournisseur = listEnteteRelationFournisseur;
        inflater = activity.getLayoutInflater();

        Log.e("adpapter" ,"RemiseExpandableListAdapter" ) ;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_entete_suivie_frns, null);
        EnteteRelationFournisseur  enteteRelationFournisseur = (EnteteRelationFournisseur) getGroup(groupPosition) ;
        Log.e("getViewGroup",""+enteteRelationFournisseur.toString()) ;

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        //mExpandableListView.expandGroup(groupPosition);

        TextView txt_code_fournisseur = (TextView) convertView.findViewById(R.id.txt_code_fournisseur);
        TextView txt_raison_fournisseur = (TextView) convertView.findViewById(R.id.txt_raison_fournisseur);

        txt_code_fournisseur.setText("("+enteteRelationFournisseur.getCodeFournisseur()+")");
        txt_raison_fournisseur.setText(enteteRelationFournisseur.getRaisonSocialFournisseur());




        return convertView;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return   this.listEnteteRelationFournisseur.get(listPosition).getListRelationFournisseur().get(expandedListPosition) ;


    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {



        convertView = inflater.inflate(R.layout.item_child_suivie_frns, null);
    //    Client clientRemise  = (Client) getGroup(groupPosition) ;

        final RelationFournisseur relFRNS = (RelationFournisseur) getChild(groupPosition, childPosition) ;
        Log.e("getViewGroup",""+relFRNS.toString()) ;

        TextView txt_num_relation = (TextView) convertView.findViewById(R.id.txt_num_relation);
        TextView txt_date_piece = (TextView) convertView.findViewById(R.id.txt_date_piece);
        TextView txt_contact = (TextView) convertView.findViewById(R.id.txt_contact);

        TextView txt_representant = (TextView) convertView.findViewById(R.id.txt_representant);
        TextView txt_objet = (TextView) convertView.findViewById(R.id.txt_objet);
        TextView txt_observation = (TextView) convertView.findViewById(R.id.txt_observation);

        TextView txt_moyen_nature = (TextView) convertView.findViewById(R.id.txt_moyen_nature);
        TextView txt_etat = (TextView) convertView.findViewById(R.id.txt_etat);



        txt_num_relation.setText(relFRNS.getNumeroRelation());
        txt_date_piece.setText(sdf.format(relFRNS.getDatePiece()));
        txt_contact.setText(relFRNS.getContact());
        txt_representant.setText(relFRNS.getRepresentant());

        txt_observation.setText(relFRNS.getObjet());
        txt_objet.setText(relFRNS.getObject());


        txt_moyen_nature.setText(relFRNS.getMoyenRelation()+" / "+relFRNS.getNatureRelation());
        txt_etat.setText(relFRNS.getEtat());



        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {

        return   this.listEnteteRelationFournisseur.get(listPosition).getListRelationFournisseur().size() ;


    }

    @Override
    public Object getGroup(int listPosition) {
        return this.listEnteteRelationFournisseur.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listEnteteRelationFournisseur.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }



    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }














    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

}