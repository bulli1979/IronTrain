package com.mirko_eberlein.irontrain.business;

/**
 * Created by Andreas on 07.04.2016.
 */
public class TrainSet {
    private String id;
    private String trainExercice;
    private int setNr;
    private float weight;
    private float repeat;

    private TrainSet(Builder builder){
        this.id = builder.id;
        this.trainExercice = builder.trainExercice;
        this.setNr = builder.setNr;
        this.weight = builder.weight;
        this.repeat = builder.repeat;
    }


    public static class Builder{
        private String id,trainExercice;
        private int setNr;
        private float weight,repeat;
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
        public Builder repeat(float repeat){
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

    public void setTrainExercice(String trainExercice) {
        this.trainExercice = trainExercice;
    }

    public int getSetNr() {
        return setNr;
    }

    public void setSetNr(int setNr) {
        this.setNr = setNr;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getRepeat() {
        return repeat;
    }

    public void setRepeat(float repeat) {
        this.repeat = repeat;
    }
}
