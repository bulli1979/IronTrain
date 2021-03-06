package com.irontrain.business;

import java.io.Serializable;

/**
 * Created by Mirko Eberlein on 16.02.2016.
 * Pojo Plan Day
 * Verantwortlich: Andreas Züger
 */
public class PlanDay implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;
    private String plan;

    private PlanDay(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.plan = builder.plan;
    }

    public static class Builder{
        private String id,name,description,plan;
        public Builder(){}
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

    @Override
    public String toString(){
        return name;
    }
}
