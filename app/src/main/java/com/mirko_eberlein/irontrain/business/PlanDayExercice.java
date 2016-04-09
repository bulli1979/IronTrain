package com.mirko_eberlein.irontrain.business;

/**
 * Created by Ebi on 07.04.2016.
 */
public class PlanDayExercice {
    private String id;
    private String planDay;
    private String exercice;
    private int setCount;

    private PlanDayExercice(Builder builder){
        this.id = builder.id;
        this.planDay = builder.planDay;
        this.exercice = builder.exercice;
        this.setCount = builder.setCount;
    }

    public static class Builder{
        private int setCount;
        private String id,planDay,exercice;

        public Builder(){}

        public Builder id(String id){
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
