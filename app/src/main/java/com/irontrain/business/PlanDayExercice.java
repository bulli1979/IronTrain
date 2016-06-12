package com.irontrain.business;

/**
 * Created by Mirko Eberlein on 07.04.2016.
 * Pojo Plan Day Exercice
 * Verantwortlich: Andreas Züger
 */
public class PlanDayExercice {
    private String id;
    private String planDay;
    private String exercice;
    private int setCount;
    private String repeat;
    private String description;
    private PlanDayExercice(Builder builder){
        this.id = builder.id;
        this.planDay = builder.planDay;
        this.exercice = builder.exercice;
        this.setCount = builder.setCount;
        this.repeat = builder.repeat;
        this.description = builder.description;
    }

    public static class Builder{
        private int setCount;
        private String id,planDay,exercice,repeat,description;

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
        public Builder setRepeatr(String repeat){
            this.repeat = repeat;
            return this;
        }
        public Builder setDescription(String description){
            this.description = description;
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

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

}
