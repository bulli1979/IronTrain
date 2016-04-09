package com.mirko_eberlein.irontrain.business;

import java.util.Date;

/**
 * Created by Ebi on 16.02.2016.
 */
public class Plan {
    private String id;
    private String name;
    private String description;
    private Date createdOn;

    public Plan(){}
    private Plan(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.createdOn = builder.createdOn;
    }

    public static class Builder{
        private String id,name,description;
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


        public Builder createdOn(Date cretedOn){
            this.createdOn =createdOn;
            return this;
        }

        public Plan build(){
            return new Plan(this);
        }

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedon(Date createdOn) {
        this.createdOn = createdOn;
    }
}
