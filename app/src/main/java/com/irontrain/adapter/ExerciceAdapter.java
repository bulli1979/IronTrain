package com.irontrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.irontrain.R;
import com.irontrain.business.Exercice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mirko Eberlein on 24.04.2016.
 * Adapter for Autocomplet TextFeld Exercices
 * Adapter steuert Filter und anzeige für Autocomplete
 * Verantwortlich: Andreas Züger
 */

public class ExerciceAdapter extends ArrayAdapter<Exercice> implements Filterable{
    private List<Exercice> items;
    private List<Exercice> allItems;
    private List<Exercice> suggestItems;

    private static class ViewHolder {
        private TextView itemView;
    }

    public ExerciceAdapter(Context context, int textViewResourceId, List<Exercice> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.allItems = getListClone();
        this.suggestItems = new ArrayList<>();
    }

    public List<Exercice> getListClone(){
        List<Exercice> allList = new ArrayList<>();
        for(Exercice e: items){
            allList.add(e);
        }
        return  allList;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
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

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Exercice)(resultValue)).getName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestItems.clear();
                for (Exercice exercice : allItems) {
                    if(exercice.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestItems.add(exercice);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestItems;
                filterResults.count = suggestItems.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        /*Unchecked has to added */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<Exercice> filteredList = (ArrayList<Exercice>) results.values;
            if(results.count > 0) {
                clear();
                for (Exercice e : filteredList) {
                    add(e);
                }
                notifyDataSetChanged();
            }
        }
    };


}