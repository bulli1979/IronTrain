/*
 * You can use only script parts but do not copy the application. All rights reserved by Mirko Eberlein.
 */

package com.irontrain.business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Mirko Eberlein on 04.06.2016.
 * Help Item to controll a train view and dont do to much connections to database
 * this is not an database Pojo
 * Verantwortlich: Fabricio Ruch
 */
public class TrainItem implements Serializable{

    private static final long serialVersionUID = 1L;

    private Plan plan;
    private PlanDay planDay;
    private List<Train> trains;
    private Date lastTrain;


    public PlanDay getPlanDay() {
        return planDay;
    }

    public void setPlanDay(PlanDay planDay) {
        this.planDay = planDay;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }

    public Plan getPlan() {

        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Date getLastTrain() {
        return lastTrain;
    }

    public void setLastTrain(Date lastTrain) {
        this.lastTrain = lastTrain;
    }
}
