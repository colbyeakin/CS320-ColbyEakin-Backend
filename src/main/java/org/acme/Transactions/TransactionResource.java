/**
 * This class defines a RESTful resource for managing transactions. 
 * It provides endpoints to create, read, update, and delete transactions.
 * The transactions are stored in a placeholder list for demonstration purposes. 
 * Each transaction has an ID, date, category, description, and amount.
 *
 * Endpoints:
 * - POST /transaction: Create a new transaction.
 * - GET /transaction: Retrieve the list of transactions.
 * - PATCH /transaction/{id}/amount: Update the amount of a specific transaction by ID
 * - DELETE /transaction/{id}: Delete a specific transaction by ID
 */

package org.acme.Transactions;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/transaction")
public class TransactionResource {

    private int nextId = 4;

    // Placeholder list of transactions
    public static List<Transaction> transactions = new ArrayList<>(List.of(
            new Transaction() {
                {
                    id = 1;
                    date = "2026-03-20";
                    category = "Housing";
                    description = "Rent";
                    amount = 500.00;
                }
            },
            new Transaction() {
                {
                    id = 2;
                    date = "2026-03-21";
                    category = "Food";
                    description = "Walmart";
                    amount = 50.76;
                }
            },
            new Transaction() {
                {
                    id = 3;
                    date = "2026-03-24";
                    category = "Transportation";
                    description = "Gas";
                    amount = 40.98;
                }
            }));

    // CREATE new transaction
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTransaction(Transaction transaction) {
        transaction.id = nextId++;
        transactions.add(0, transaction);
        return Response.status(Response.Status.CREATED)
                .entity(transaction).build();
    }

    // READ - get list of transactions
    @GET
    public Response getTransactions() {
        return Response.ok(transactions).build();
    }

    // GET - transaction by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionById(@PathParam("id") int id) {
        for (Transaction transaction : transactions) {
            if (transaction.id == id) {
                return Response.ok(transaction).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // UPDATE - partially update transaction by ID
    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTransaction(@PathParam("id") int id, Transaction updatedTransaction) {

        for (Transaction transaction : transactions) {
            if (transaction.id == id) {

                // Only update fields that are provided (PATCH behavior)
                if (updatedTransaction.getDate() != null) {
                    transaction.setDate(updatedTransaction.getDate());
                }

                if (updatedTransaction.getCategory() != null) {
                    transaction.setCategory(updatedTransaction.getCategory());
                }

                if (updatedTransaction.getDescription() != null) {
                    transaction.setDescription(updatedTransaction.getDescription());
                }

                // Assuming amount is a primitive double, adjust if it's Double
                if (updatedTransaction.getAmount() != null) {
                    transaction.setAmount(updatedTransaction.getAmount());
                }

                // Return updated transaction (better for frontend sync)
                return Response.ok(transaction).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Transaction with ID " + id + " not found.")
                .build();
    }

    // DELETE transaction
    @DELETE
    @Path("/{id}")
    public Response deleteTransaction(@PathParam("id") int id) {
        for (Transaction transaction : transactions) {
            if (transaction.id == id) {
                transactions.remove(transaction);
                return Response.ok("Transaction with ID '" + id + "' was deleted.").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Transaction with ID '" + id + "' was not found.").build();
    }

}
