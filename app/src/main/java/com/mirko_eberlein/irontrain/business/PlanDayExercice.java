package com.mirko_eberlein.irontrain.business;

/**
 * Created by Mirko on 28.03.2016.
 */
public class PlanDayExercice {
    private int id;
    private String planDay;
    private String exercice;
    private int setCount;

    public PlanDayExercice(){}

    private PlanDayExercice(Builder builder){
        this.id = builder.id;
        this.planDay = builder.planDay;
        this.exercice = builder.exercice;
        this.setCount = builder.setCount;
    }

    public static class Builder{
        private int id;
        private String planDay;
        private String exercice;
        private int setCount;
        public Builder(){}
        public Builder id(int id){
            this.id = id;
            return this;
        }
        public Builder planDay(String planDay){
            this.planDay = planDay;
            return this;
        }

        public Builder exercice(String exercice){
            this.exercice = exercice;
            return this;
        }

        public Builder setCount(int setCount){
            this.setCount = setCount;
            return this;
        }

        public PlanDayExercice build(){
            return new PlanDayExercice(this);
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

    public String getExercice() {
        return exercice;
    }

    public void setExercice(String exercice) {
        this.exercice = exercice;
    }

    public int getSetCount() {
        return setCount;
    }

    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }
}
