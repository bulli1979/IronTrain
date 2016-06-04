/*
 * You can use only script parts but do not copy the application. All rights reserved by Mirko Eberlein.
 */

package com.irontrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.irontrain.R;
import com.irontrain.business.PlanDay;
import com.irontrain.business.TrainItem;

import java.util.List;

/**
 * Created by Ebi on 24.04.2016.
 * Adapter for Plan Day List
 */

public class TrainItemAdapter extends ArrayAdapter<TrainItem> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public TrainItemAdapter(Context context, int textViewResourceId, List<TrainItem> items) {
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

        TrainItem item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            String planName = item.getPlan().getName();
            String name = item.getPlanDay().getName();
            int count = item.getTrains().size();
            viewHolder.itemView.setText(planName + " - " + name + " ("+count+")");

        }

        return convertView;
    }
}