package org.acme.Dashboard;

import java.util.List;

public class BreakdownResponse {
    public double totalSpent;
    public List<CategoryBreakdownItem> breakdown;

    public BreakdownResponse(double totalSpent, List<CategoryBreakdownItem> breakdown) {
        this.totalSpent = totalSpent;
        this.breakdown = breakdown;
    }
}