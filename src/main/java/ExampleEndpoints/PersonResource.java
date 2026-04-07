package ExampleEndpoints;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    // CREATE
    @POST
    @Transactional
    public Response addPerson(Person person) {
        person.persist();
        return Response.status(Response.Status.CREATED)
                .entity(person)
                .build();
    }

    // READ
    @GET
    public Response getPeople() {
        return Response.ok(Person.listAll()).build();
    }

    // READ - get person by ID
    @GET
    @Path("/{id}")
    public Response getPersonById(@PathParam("id") long id) {
        Person person = Person.findById(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Person with ID " + id + " not found.")
                    .build();
        }

        return Response.ok(person).build();
    }

    // UPDATE - change age
    @PATCH
    @Path("/{id}/age")
    @Transactional
    public Response updatePersonAge(@PathParam("id") long id, Integer newAge) {
        Person person = Person.findById(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Person with ID " + id + " not found.")
                    .build();
        }

        if (newAge == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A new age must be provided.")
                    .build();
        }

        person.age = newAge;
        return Response.ok(person).build();
    }

    // UPDATE - change entire database entry
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updatePerson(@PathParam("id") long id, Person updatedPerson) {
        Person person = Person.findById(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Person with ID " + id + " not found.")
                    .build();
        }

        person.name = updatedPerson.name;
        person.age = updatedPerson.age;
        person.favoriteThing = updatedPerson.favoriteThing;

        return Response.ok(person).build();
    }

    // DELETE
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePerson(@PathParam("id") long id) {
        Person person = Person.findById(id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Person with ID " + id + " not found.")
                    .build();
        }

        person.delete();
        return Response.ok("Person with ID " + id + " has been deleted.").build();
    }
}
