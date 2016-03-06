package com.mirko_eberlein.irontrain.business;

import java.util.Date;

/**
 * Created by Ebi on 16.02.2016.
 */
public class Plan {
    private String id;
    private String name;
    private String description;
    private Date createdon;

    public Plan(){}
    public Plan(String id,String name,String description,Date createdon){
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdon = createdon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }
}
