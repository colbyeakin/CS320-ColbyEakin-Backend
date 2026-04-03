/**
 * SummaryResponse is a simple class that represents the response for a summary of transactions.
 */

package org.acme.Transactions;

public class SummaryResponse {
    public double totalSpent;

    public SummaryResponse(double totalSpent) {
        this.totalSpent = totalSpent;
    }
}
