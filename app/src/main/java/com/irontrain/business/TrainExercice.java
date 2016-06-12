package com.irontrain.business;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko Eberlein on 07.04.2016.
 * Pojo for TrainExercice
 * Verantwortlich: Fabricio Ruch
 */
public class TrainExercice implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String planDayExercice;
    private String train;
    private String exerciceTitle;
    private String exerciceDescription;
    private List<TrainSet> trainSetList;

    private TrainExercice(Builder builder){
        this.id = builder.id;
        this.planDayExercice = builder.planDayExercice;
        this.train = builder.train;
        this.exerciceDescription = builder.exerciceDescription;
        this.exerciceTitle = builder.exerciceTitle;
    }

    public static class Builder{
        private String id,planDayExercice,train;
        private String exerciceTitle;
        private String exerciceDescription;
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
        public Builder exerciceTitle(String exerciceTitle){
            this.exerciceTitle = exerciceTitle;
            return this;
        }
        public Builder exerciceDescription(String exerciceDescription){
            this.exerciceDescription = exerciceDescription;
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

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public List<TrainSet> getTrainSetList() {
        return trainSetList;
    }

    public void setTrainSetList(List<TrainSet> trainSetList) {
        this.trainSetList = trainSetList;
    }

    public String getExerciceDescription() {
        return exerciceDescription;
    }
    public String getExerciceTitle() {
        return exerciceTitle;
    }
}
