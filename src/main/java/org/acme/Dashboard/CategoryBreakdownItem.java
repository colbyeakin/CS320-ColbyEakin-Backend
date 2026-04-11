package org.acme.Dashboard;

public class CategoryBreakdownItem {
    public String category;
    public double amount;
    public double percent;

    public CategoryBreakdownItem(String category, double amount, double percent) {
        this.category = category;
        this.amount = amount;
        this.percent = percent;
    }
}
