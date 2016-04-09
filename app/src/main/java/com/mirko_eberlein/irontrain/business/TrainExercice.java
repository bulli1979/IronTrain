package com.mirko_eberlein.irontrain.business;

/**
 * Created by Farbricio on 07.04.2016.
 */
public class TrainExercice {
    private String id;
    private String planDayExercice;
    private String train;

    private TrainExercice(Builder builder){
        this.id = builder.id;
        this.planDayExercice = builder.planDayExercice;
        this.train = builder.train;
    }

    public static class Builder{
        private String id,planDayExercice,train;
        public Builder id(String id){
            this.id = id;
            return this;
        }
        public Builder planDayExercice(String planDayExercice){
            this.planDayExercice = planDayExercice;
            return this;
        }
        public Builder train(String train){
            this.train = train;
            return this;
        }

        public TrainExercice build(){
            return new TrainExercice(this);
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanDayExercice() {
        return planDayExercice;
    }

    public void setPlanDayExercice(String planDayExercice) {
        this.planDayExercice = planDayExercice;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }
}
