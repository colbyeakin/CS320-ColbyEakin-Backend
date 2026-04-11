package org.acme.Goals;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "goals")
public class Goal extends PanacheEntity {

    public String goalName;
    public Double goalAmount;
    public Double amountSaved;
    public String targetDate;

    public Goal() {
        this.goalName = "";
        this.amountSaved = 0.0;
        this.goalAmount = 0.0;
        this.targetDate = "";
    }

    public Goal(String goalName, Double goalAmount, Double amountSaved, String targetDate) {
        this.goalName = goalName;
        this.goalAmount = goalAmount;
        this.amountSaved = amountSaved;
        this.targetDate = targetDate;
    }

    public String getGoalName() {
        return goalName;
    }

    public Double getAmountSaved() {
        return amountSaved;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public Double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setGoalAmount(Double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public void setAmountSaved(Double amountSaved) {
        this.amountSaved = amountSaved;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

}
