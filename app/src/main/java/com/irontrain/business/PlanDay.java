package com.irontrain.business;

import java.util.Date;

/**
 * Created by Ebi on 16.02.2016.
 */
public class PlanDay {
    private String id;
    private String name;
    private String description;
    private String plan;
    private Date createdOn;

    private PlanDay(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.plan = builder.plan;
        this.createdOn = builder.createdOn;
    }

    public static class Builder{
        private String id,name,description,plan;
        private Date createdOn;
        public Builder(){};
        public Builder id(String id){
            this.id = id;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder plan(String plan){
            this.plan = plan;
            return this;
        }
        public Builder createdOn(Date createdOn){
            this.createdOn = createdOn;
            return this;
        }

        public PlanDay build(){
            return new PlanDay(this);
        }

    }

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

    public Date getCreatedOn(){return createdOn;}

    @Override
    public String toString(){
        return name;
    }
}
