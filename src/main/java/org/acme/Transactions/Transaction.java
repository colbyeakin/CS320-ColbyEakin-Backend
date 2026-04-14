/**
 * This class represents a financial transaction with properties such as 
 * id, date, category, description, and amount. It includes getter methods 
 * for each property and a setter method for the amount.
 * 
 */

package org.acme.Transactions;

import java.time.OffsetDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction extends PanacheEntity {
    public OffsetDateTime date;
    public String category;
    public String description;
    public Double amount;

    public OffsetDateTime getDate() {
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

    public void setDate(OffsetDateTime date) {
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
