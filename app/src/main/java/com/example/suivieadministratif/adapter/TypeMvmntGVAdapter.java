package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.TypeMouvementClick;

import java.util.ArrayList;

public class TypeMvmntGVAdapter extends BaseAdapter {
    private Activity activity;
    ArrayList<TypeMouvementClick> listType;

    public TypeMvmntGVAdapter(Activity activity, ArrayList<TypeMouvementClick> listType) {
        this.activity = activity;
        this.listType = listType;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        final TypeMouvementClick currentType = listType.get(position);

        gridView = inflater.inflate(R.layout.item_titre_type_mvmnt_gv, null);

        final RelativeLayout ll_item = gridView.findViewById(R.id.btn_item);
        final TextView txt_libelle_type = gridView.findViewById(R.id.txt_libelle_type);

        txt_libelle_type.setText(currentType.getLibelle());


        if (currentType.getNbrClick() == 0) {

            ll_item.setBackground(activity.getResources().getDrawable(R.drawable.round_cadre_inter));
            txt_libelle_type.setTextColor(activity.getResources().getColor(R.color.colorPrimary));

        } else if (currentType.getNbrClick() == 1) {
            ll_item.setBackground(activity.getResources().getDrawable(R.drawable.round_intervenant));
            txt_libelle_type.setTextColor(activity.getResources().getColor(R.color.white));

        }


        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentType.getNbrClick() == 0) {

                    ll_item.setBackground(activity.getResources().getDrawable(R.drawable.round_intervenant));
                    txt_libelle_type.setTextColor(activity.getResources().getColor(R.color.white));
                    currentType.setNbrClick(1);

                } else if (currentType.getNbrClick() == 1) {
                    ll_item.setBackground(activity.getResources().getDrawable(R.drawable.round_cadre_inter));
                    txt_libelle_type.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
                    currentType.setNbrClick(0);
                }

            }
        });

        return gridView;
    }

    @Override
    public int getCount() {
        //Log.e("SIZE_BASE",listSupplement.size()+"");
        return listType.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
