/**
 * This class is responsible for representing the response of the dashboard endpoint. 
 * It contains the total budget, total spent, and amount remaining for the user. 
 * The amount remaining is calculated by subtracting the total spent from the total budget. 
 * This class is used to send the dashboard data back to the client in a structured format.
 */

package org.acme.Dashboard;

public class DashboardResponse {
    public double totalBudget;
    public double totalSpent;
    public double amountRemaining;

    public DashboardResponse(double totalBudget, double totalSpent) {
        this.totalBudget = totalBudget;
        this.totalSpent = totalSpent;
        this.amountRemaining = totalBudget - totalSpent;
    }
}
