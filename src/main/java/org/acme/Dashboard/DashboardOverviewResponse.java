package org.acme.Dashboard;

import java.util.List;

import org.acme.Goals.Goal;

public class DashboardOverviewResponse {
    public double totalBudget;
    public double totalSpent;
    public double amountRemaining;
    public List<CategoryBreakdownItem> breakdown;
    public List<Goal> goals;

    public DashboardOverviewResponse(
            double totalBudget,
            double totalSpent,
            List<CategoryBreakdownItem> breakdown,
            List<Goal> goals) {
        this.totalBudget = totalBudget;
        this.totalSpent = totalSpent;
        this.amountRemaining = totalBudget - totalSpent;
        this.breakdown = breakdown;
        this.goals = goals;
    }
}
