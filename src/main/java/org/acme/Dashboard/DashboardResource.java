/**
 * This class is responsible for handling the dashboard resource. It allows users to get the current 
 * budget and total spent, as well as update the total budget. The dashboard data is calculated based 
 * on the transactions stored in the TransactionResource class. The total spent is calculated by summing 
 * the amounts of all transactions, and the total budget can be updated through a PATCH request. 
 * The dashboard response is returned as a JSON object containing the total budget and total spent.
 */

package org.acme.Dashboard;

import org.acme.Transactions.Transaction;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dashboard")
public class DashboardResource {

    private double totalBudget = 5000.0;

    // GET endpoint to retrieve dashboard data
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashboard() {
        double totalSpent = calculateTotalSpent();
        DashboardResponse response = new DashboardResponse(totalBudget, totalSpent);
        return Response.ok(response).build();
    }

    // GET endpoint to retrieve category breakdown
    @GET
    @Path("/breakdown")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBreakdown() {
        try {
            var transactions = Transaction.<Transaction>listAll();

            // Calculate total spent
            double totalSpent = transactions.stream()
                    .map(Transaction::getAmount)
                    .filter(amount -> amount != null)
                    .mapToDouble(Double::doubleValue)
                    .sum();

            // Group by category
            var categoryTotals = transactions.stream()
                    .filter(t -> t.getCategory() != null && t.getAmount() != null)
                    .collect(java.util.stream.Collectors.groupingBy(
                            Transaction::getCategory,
                            java.util.stream.Collectors.summingDouble(Transaction::getAmount)));

            // Convert to response objects
            var breakdownList = categoryTotals.entrySet().stream()
                    .map(entry -> {
                        double amount = entry.getValue();
                        double percent = totalSpent > 0
                                ? Math.round((amount / totalSpent) * 10000.0) / 100.0
                                : 0;

                        return new CategoryBreakdownItem(
                                entry.getKey(),
                                amount,
                                percent);
                    })
                    .sorted((a, b) -> Double.compare(b.amount, a.amount))
                    .toList();

            return Response.ok(new BreakdownResponse(totalSpent, breakdownList)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error generating breakdown")
                    .build();
        }
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
        return Transaction.<Transaction>listAll().stream()
                .map(Transaction::getAmount)
                .filter(amount -> amount != null)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}