package com.irontrain.business;

import java.util.Date;

/**
 * Created by Ebi on 07.04.2016.
 */
public class Train {
    private String id;
    private String planDay;
    private Date date;

    private Train(Builder builder){
        this.id = builder.id;
        this.planDay = builder.planDay;
        this.date = builder.date;
    }
    public static class Builder{
        private String id;
        private String planDay;
        private Date date;
        public Builder id(String id){
            this.id = id;
            return this;
        }
        public Builder planDay(String planDay){
            this.planDay = planDay;
            return this;
        }
        public Builder date(Date date){
            this.date = date;
            return this;
        }
        public Train build(){
            return new Train(this);
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanDay() {
        return planDay;
    }

    public void setPlanDay(String planDay) {
        this.planDay = planDay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}