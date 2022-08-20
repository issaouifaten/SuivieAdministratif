package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Article_BL_JAV;
import com.example.suivieadministratif.model.ImpayeClient;
import com.example.suivieadministratif.model.ImpayeClientEntete;
import com.example.suivieadministratif.model.PieceRecouvrementEtendu;
import com.example.suivieadministratif.model.TypePiece;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RegEtenduClientELVAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<TypePiece> listTypePiece;
    public LayoutInflater inflater;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public RegEtenduClientELVAdapter(Activity activity, ArrayList<TypePiece> listTypePiece) {
        this.activity = activity;
        this.listTypePiece = listTypePiece;
        inflater = activity.getLayoutInflater();
        Log.e("adapterx", "Constructeur");
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_entete_type_piece, null);
        TypePiece typePiece = (TypePiece) getGroup(groupPosition);

        CardView item_gratuite_entete = (CardView) convertView.findViewById(R.id.item_gratuite_entete);

        TextView txt_libelle = (TextView) convertView.findViewById(R.id.txt_libelle);
        TextView txt_total_par_type = (TextView) convertView.findViewById(R.id.txt_total_par_type);
        txt_libelle.setText(typePiece.getLibelle());


        final NumberFormat decF = NumberFormat.getNumberInstance(Locale.FRENCH);
        decF.setMinimumFractionDigits(3);
        decF.setMaximumFractionDigits(3);

         txt_total_par_type.setText(decF.format(typePiece.getTotal_mnt()));

        if (groupPosition % 2 == 0) // paire
        {
            item_gratuite_entete.setCardBackgroundColor(activity.getResources().getColor(R.color.rouge_clair));
        } else {
            item_gratuite_entete.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
        }

        Log.e("adapterx", "getGroupView");
        return convertView;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {

        Log.e("adapterx", "getChild");
        return this.listTypePiece.get(listPosition).getListPieceRecouvEtendu().get(expandedListPosition);

     /*   return this.expandableListDetail.get(this.listClientRemise.get(listPosition))
                .get(expandedListPosition);*/
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        Log.e("adapterx", "getChildId");
        return expandedListPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View rowView, ViewGroup parent) {

        rowView = inflater.inflate(R.layout.item_piece_client_etendu, null);

        TextView txt_num_piece = (TextView) rowView.findViewById(R.id.txt_num_piece);
        TextView txt_date_piece = (TextView) rowView.findViewById(R.id.txt_date_piece);
        TextView txt_montant_piece = (TextView) rowView.findViewById(R.id.txt_montant_piece);
        TextView txt_libelle = (TextView) rowView.findViewById(R.id.txt_libelle);

        TextView txt_mnt_regle = (TextView) rowView.findViewById(R.id.txt_mnt_regle);
        TextView txt_restant_due = (TextView) rowView.findViewById(R.id.txt_restant_due);
        TextView txt_marge_dt = (TextView) rowView.findViewById(R.id.txt_marge_dt);
        TextView txt_marge_pourcent = (TextView) rowView.findViewById(R.id.txt_marge_pourcent);


        final PieceRecouvrementEtendu pieceRecEtendu = (PieceRecouvrementEtendu) getChild(groupPosition, childPosition);

        txt_libelle.setText(pieceRecEtendu.getLibelle());
        txt_num_piece.setText(pieceRecEtendu.getNumeroPiece());
        txt_date_piece.setText(sdf.format(pieceRecEtendu.getDatePiece()));

        final NumberFormat decF = NumberFormat.getNumberInstance(Locale.FRENCH);
        decF.setMinimumFractionDigits(3);
        decF.setMaximumFractionDigits(2);

        DecimalFormat   pourcentFormat  = new DecimalFormat("0.00")  ;
        txt_montant_piece.setText(decF.format(pieceRecEtendu.getMontant()));
        txt_mnt_regle.setText(decF.format(pieceRecEtendu.getMontantRegle()));
        txt_restant_due.setText(decF.format(pieceRecEtendu.getRestantDue()));
        txt_marge_dt.setText(decF.format(pieceRecEtendu.getMargeDt()));
        txt_marge_pourcent.setText(pourcentFormat.format(pieceRecEtendu.getMargePourcent()) + " %");

        Log.e("adapterx", "getChildView");

        return rowView;
    }

    @Override
    public int getChildrenCount(int listPosition) {

        Log.e("adapterx", "getChildrenCount");
        return this.listTypePiece.get(listPosition).getListPieceRecouvEtendu().size();

        /* return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) .size();*/
    }

    @Override
    public Object getGroup(int listPosition) {
        Log.e("adapterx", "getGroup");
        return this.listTypePiece.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        Log.e("adapterx", "getGroupCount");
        return this.listTypePiece.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        Log.e("adapterx", "getGroupId");
        return listPosition;
    }


    @Override
    public boolean hasStableIds() {
        Log.e("adapterx", "hasStableIds");
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        Log.e("adapterx", "isChildSelectable");
        return true;
    }


    @Override
    public void onGroupCollapsed(int groupPosition) {
        Log.e("adapterx", "onGroupCollapsed");
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Log.e("adapterx", "onGroupExpanded");
        super.onGroupExpanded(groupPosition);
    }


}