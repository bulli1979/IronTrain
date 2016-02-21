package com.mirko_eberlein.irontrain.business;

import java.util.Date;

/**
 * Created by Ebi on 16.02.2016.
 */
public class PlanDay {
    private String id;
    private String name;
    private String description;
    private String plan;
    private Date d;
    //comment
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
