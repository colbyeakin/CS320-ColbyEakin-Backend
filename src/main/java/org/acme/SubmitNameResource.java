package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/submit-name")
public class SubmitNameResource {
    // In-memory list to store usernames
    List<String> usernames = new ArrayList<>(List.of("Fred", "Daphne", "Velma", "Shaggy", "Scooby"));

    // Retrieve the list of names
    @GET
    public Response getNames() {
        return Response.ok(usernames).build();
    }

    // Add a new name to the list
    @POST
    public Response addName(String name) {
        if (usernames.contains(name)) { // INPUT VALIDATION: check if name already exists
            return Response.status(Response.Status.CONFLICT)
                    .entity("Name '" + name + "' already exists.").build();
        } else { // add the new name
            usernames.add(name);
            return Response.status(Response.Status.CREATED)
                    .entity("Name '" + name + "' has been added.").build();
        }
    }
}
