/*
 * You can use only script parts but do not copy the application. All rights reserved by Mirko Eberlein.
 */

package com.irontrain.business;

import java.util.Date;
import java.util.List;

/**
 * Created by Ebi on 04.06.2016.
 * Help Item to controll a train view and dont do to much connections to database
 */
public class TrainItem {
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
