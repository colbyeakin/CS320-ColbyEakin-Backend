package org.acme;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/create-category")
public class CreateCategoryResource {
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
        if (categories.contains(category)) { // Checks if category already exists
            return Response.status(Response.Status.CONFLICT)
                    .entity("Category '" + category + "' already exists.").build();
        } else {
            categories.add(category);
            return Response.status(Response.Status.CREATED)
                    .entity("Category '" + category + "' has been added.").build();
        }
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