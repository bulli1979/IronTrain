package com.irontrain.business;

/**
 * Created by Ebi on 07.04.2016.
 */
public class PlanDayExercice {
    private String id;
    private String planDay;
    private String exercice;
    private int setCount;
    private int repeat;
    private PlanDayExercice(Builder builder){
        this.id = builder.id;
        this.planDay = builder.planDay;
        this.exercice = builder.exercice;
        this.setCount = builder.setCount;
        this.repeat = builder.repeat;
    }

    public static class Builder{
        private int setCount,repeat;
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
        public Builder setRepeatr(int repeat){
            this.repeat = repeat;
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

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}
