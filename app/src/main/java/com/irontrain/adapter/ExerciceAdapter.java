package com.irontrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.irontrain.R;
import com.irontrain.business.Exercice;
import com.irontrain.business.PlanDay;

import java.util.List;

/**
 * Created by Ebi on 24.04.2016.
 */

public class ExerciceAdapter extends ArrayAdapter<Exercice> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public ExerciceAdapter(Context context, int textViewResourceId, List<Exercice> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.custom_exercice_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.exerciceTitle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Exercice item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.getName());
        }

        return convertView;
    }
}