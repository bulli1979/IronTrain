package com.irontrain.business;

import java.io.Serializable;

/**
 * Created by Mirko Eberlein on 07.04.2016.
 * Pojo TrainSet
 * Verantwortlich: Fabricio Ruch
 */
public class TrainSet implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String trainExercice;
    private int setNr,repeat;
    private float weight;

    private TrainSet(Builder builder){
        this.id = builder.id;
        this.trainExercice = builder.trainExercice;
        this.setNr = builder.setNr;
        this.weight = builder.weight;
        this.repeat = builder.repeat;
    }


    public static class Builder{
        private String id,trainExercice;
        private int setNr,repeat;
        private float weight;
        public Builder id(String id){
            this.id = id;
            return this;
        }
        public Builder trainExercice(String trainExercice){
            this.trainExercice = trainExercice;
            return this;
        }
        public Builder setNr(int setNr){
            this.setNr = setNr;
            return this;
        }
        public Builder weight(float weight){
            this.weight = weight;
            return this;
        }
        public Builder repeat(int repeat){
            this.repeat = repeat;
            return this;
        }
        public TrainSet build(){
            return new TrainSet(this);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainExercice() {
        return trainExercice;
    }

    public int getSetNr() {
        return setNr;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}
