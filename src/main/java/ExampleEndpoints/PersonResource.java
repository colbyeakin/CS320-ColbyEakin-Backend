package ExampleEndpoints;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/person")
public class PersonResource {

    // CREATE
    @POST
    @Transactional
    public Response addPerson(Person person) {
        person.persist();
        return Response.status(Response.Status.CREATED)
                .entity("Person '" + person.name + "' has been created.").build();
    }

    // READ - version 1 - get all
    @GET
    public Response getAllPeople() {
        return Response.ok(Person.listAll()).build();
    }

    // READ - version 2 - get by ID
    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        Person person = Person.findById(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Person with ID " + id + " not found.").build();
        }
        return Response.ok(person).build();
    }

    // UPDATE - change entire database entry
    @PATCH
    @Path("/{id}")
    @Transactional
    public Response updatePerson(@PathParam("id") Long id, Person updatedPerson) {
        Person currentPerson = Person.findById(id);
        if (currentPerson == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Person with ID " + id + " not found.").build();
        }

        // if the updated fields are not null, update them
        if (updatedPerson.name != null)
            currentPerson.name = updatedPerson.name;
        if (updatedPerson.age != 0)
            currentPerson.age = updatedPerson.age;
        if (updatedPerson.favoriteThing != null)
            currentPerson.favoriteThing = updatedPerson.favoriteThing;

        return Response.ok("Person with ID " + id + " has been updated.").build();
    }

    // DELETE
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePerson(@PathParam("id") Long id) {
        Person person = Person.findById(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Person with ID " + id + " not found.").build();
        }

        person.delete();
        return Response.ok("Person with ID " + id + " has been deleted.").build();
    }
}
