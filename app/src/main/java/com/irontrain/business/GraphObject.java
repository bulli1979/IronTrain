/*
 * You can use only script parts but do not copy the application. All rights reserved by Mirko Eberlein.
 */

package com.irontrain.business;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mirko Eberlein on 05.06.2016.
 * Class to realize the object for graph
 *Verantwortlich: Mirko Eberlein
 */
public class GraphObject {
    @SuppressWarnings("unchecked")
    private List<Entry> entries = new ArrayList();
    private String exerciceName;

    public GraphObject(String exerciceName){
        this.exerciceName = exerciceName;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public String getExerciceName() {
        return exerciceName;
    }
}
