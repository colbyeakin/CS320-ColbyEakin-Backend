/**
 * This class is responsible for handling the dashboard resource. It allows users to get the current 
 * budget and total spent, as well as update the total budget. The dashboard data is calculated based 
 * on the transactions stored in the TransactionResource class. The total spent is calculated by summing 
 * the amounts of all transactions, and the total budget can be updated through a PATCH request. 
 * The dashboard response is returned as a JSON object containing the total budget and total spent.
 */

package org.acme.Dashboard;

import org.acme.Transactions.TransactionResource;
import org.acme.Transactions.Transaction;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/dashboard")
public class DashboardResource {

    private double totalBudget = 5000.0;

    private List<Transaction> transactions = TransactionResource.transactions;

    // GET endpoint to retrieve dashboard data
    @GET
    public Response getDashboard() {
        double totalSpent = calculateTotalSpent();
        DashboardResponse response = new DashboardResponse(totalBudget, totalSpent);
        return Response.ok(response).build();
    }

    // PATCH endpoint to update the total budget
    @PATCH
    @Path("/budget")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBudget(BudgetUpdateRequest request) {
        this.totalBudget = request.totalBudget;

        return Response.ok(
                new DashboardResponse(
                        totalBudget,
                        calculateTotalSpent()))
                .build();
    }

    private double calculateTotalSpent() {
        return transactions.stream()
                .mapToDouble(t -> t.getAmount())
                .sum();
    }
}