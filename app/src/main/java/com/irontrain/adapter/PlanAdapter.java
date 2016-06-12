package com.irontrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.irontrain.R;
import com.irontrain.business.Plan;

import java.util.List;

/**
 * Created by Mirko Eberlein on 24.04.2016.
 * adapter for planlist handle plan item
 * using custom_plan_List_item
 * Verantwortlich Mirko Eberlein
 */

public class PlanAdapter extends ArrayAdapter<Plan> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public PlanAdapter(Context context, int textViewResourceId, List<Plan> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.custom_plan_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.planListTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Plan item = getItem(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.getName());
        }

        return convertView;
    }
}