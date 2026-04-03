/**
 * This class represents a financial transaction with properties such as 
 * id, date, category, description, and amount. It includes getter methods 
 * for each property and a setter method for the amount.
 * 
 */

package org.acme.Transactions;

public class Transaction {
    public int id;
    public String date;
    public String category;
    public String description;
    public Double amount;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
