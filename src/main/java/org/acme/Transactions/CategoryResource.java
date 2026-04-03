/**
 * This class is responsible for handling the category resource. It allows users to get existing categories, 
 * add new categories, and delete existing categories. The categories are stored in an ArrayList and are 
 * initialized with some default categories. 
 * 
 * ENDPOINTS:
 * - GET /create-category: Returns a list of existing categories.
 * - POST /create-category: Adds a new category to the list. The category name is passed in the request body.
 * - DELETE /create-category: Deletes an existing category from the list. The category name is passed in the request body.
 */

package org.acme.Transactions;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/create-category")
public class CategoryResource {
    // Default Categorys
    ArrayList<String> categories = new ArrayList<>(
            List.of("Housing", "Food", "Transportation", "Entertainment", "Utilities"));

    // Get existing categorys
    @GET
    public Response getCategorys() {
        return Response.ok(categories).build();
    }

    // Adds new category to the list
    @POST
    public Response addCategory(String category) {
        if (category == null || category.trim().isEmpty()) { // Checks if category is null or empty
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Category name cannot be null or empty.").build();
        }

        // Normalize input
        String trimmedCategory = category.trim();

        // Check for invalid charcters (only allows (A-Z, a-z))
        if (!trimmedCategory.matches("^[a-zA-Z ]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Category name can only contain letters (A-Z).").build();
        }

        // Check for case-insensitive duplicates
        for (String existing : categories) {
            if (existing.equalsIgnoreCase(trimmedCategory)) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Category '" + trimmedCategory + "' already exists.").build();
            }
        }

        // Normalize format (capitalize first letter)
        String formattedCategory = trimmedCategory.substring(0, 1).toUpperCase()
                + trimmedCategory.substring(1).toLowerCase();

        categories.add(formattedCategory);

        return Response.status(Response.Status.CREATED)
                .entity("Category '" + formattedCategory + "' has been added.").build();
    }

    // Delete new category from the list
    @DELETE
    public Response deleteCategory(String category) {
        if (categories.contains(category)) {
            categories.remove(category);
            return Response.ok("Category '" + category + "' has been removed.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Category '" + category + "' was not found.").build();
        }

    }

}