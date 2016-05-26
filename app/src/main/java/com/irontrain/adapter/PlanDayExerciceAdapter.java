package com.irontrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.irontrain.R;
import com.irontrain.business.Exercice;
import com.irontrain.business.PlanDayExercice;
import com.irontrain.storage.daos.DAOExercice;

import java.util.List;

/**
 * Created by Ebi on 24.04.2016.
 * Adapater for PlanDay Exercice
 */

public class PlanDayExerciceAdapter extends ArrayAdapter<PlanDayExercice> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public PlanDayExerciceAdapter(Context context, int textViewResourceId, List<PlanDayExercice> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.custom_planday_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.planDayListTitle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PlanDayExercice item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            Exercice ex = DAOExercice.getExerciceById(convertView.getContext(),item.getExercice());
            viewHolder.itemView.setText(ex.getName());
        }

        return convertView;
    }
}