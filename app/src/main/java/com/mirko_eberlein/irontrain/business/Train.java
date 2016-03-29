package com.mirko_eberlein.irontrain.business;

import java.util.Date;

/**
 * Created by Mirko on 28.03.2016.
 */
public class Train {
    private int id;
    private String planDay;
    private Date date;

    public Train(Builder builder){
        this.id = builder.id;
        this.date = builder.date;
        this.planDay = builder.planDay;
    }
    public static class Builder{
        public Builder(){};
        private int id;
        private String planDay;
        private Date date;

        public Builder id(int id){
            this.id = id;
            return this;
        }

        public Builder date(Date date){
            this.date = date;
            return this;
        }

        public Builder planDay(String planDay){
            this.planDay = planDay;
            return this;
        }

        public Train build(){
            return new Train(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
