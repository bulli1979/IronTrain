package com.irontrain.business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Ebi on 07.04.2016.
 * Pjo Train
 */
public class Train implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String planDay;
    private Date date;
    private boolean finished;
    private List<TrainExercice> trainExerciceList;

    private Train(Builder builder){
        this.id = builder.id;
        this.planDay = builder.planDay;
        this.date = builder.date;
        this.finished = builder.finished;
    }
    public static class Builder{
        private String id;
        private String planDay;
        private Date date;
        private boolean finished;
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
        public Builder finished(boolean finished){
            this.finished = finished;
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

    public List<TrainExercice> getTrainExerciceList() {
        return trainExerciceList;
    }

    public void setTrainExerciceList(List<TrainExercice> trainExerciceList) {
        this.trainExerciceList = trainExerciceList;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
