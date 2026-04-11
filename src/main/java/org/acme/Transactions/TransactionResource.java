/**
 * This class defines a RESTful resource for managing transactions. 
 * It provides endpoints to create, read, update, and delete transactions.
 * The transactions are stored in a placeholder list for demonstration purposes. 
 * Each transaction has an ID, date, category, description, and amount.
 *
 * Endpoints:
 * - POST /transaction: Create a new transaction.
 * - GET /transaction: Retrieve the list of transactions.
 * - PATCH /transaction/{id}: Update a specific transaction by ID
 * - DELETE /transaction/{id}: Delete a specific transaction by ID
 */

package org.acme.Transactions;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/transaction")
public class TransactionResource {

    // CREATE new transaction
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTransaction(Transaction transaction) {
        transaction.persist();
        return Response.status(Response.Status.CREATED)
                .entity(transaction).build();
    }

    // READ - get list of transactions
    @GET
    public Response getTransactions() {
        return Response.ok(Transaction.listAll()).build();
    }

    // GET - transaction by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionById(@PathParam("id") Long id) {
        Transaction transaction = Transaction.findById(id);
        if (transaction != null) {
            return Response.ok(transaction).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // UPDATE - partially update transaction by ID
    @PATCH
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTransaction(@PathParam("id") Long id, Transaction updatedTransaction) {

        Transaction transaction = Transaction.findById(id);
        if (transaction == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Transaction with ID " + id + " not found.")
                    .build();
        }

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

    // DELETE transaction
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTransaction(@PathParam("id") Long id) {
        Transaction transaction = Transaction.findById(id);
        if (transaction == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Transaction with ID '" + id + "' was not found.").build();
        }

        transaction.delete();
        return Response.ok("Transaction with ID '" + id + "' has been deleted.").build();
    }

}
