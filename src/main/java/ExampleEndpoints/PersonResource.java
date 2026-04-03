package ExampleEndpoints;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.ArrayList;
import java.util.List;
import jakarta.ws.rs.core.Response;

@Path("/person")
public class PersonResource {
    List<Person> people = new ArrayList<>(List.of(
            new Person() {
                {
                    id = 1;
                    name = "Harry Potter";
                    age = 11;
                    favoriteThing = "Quidditch";
                }
            },
            new Person() {
                {
                    id = 2;
                    name = "Hermione Granger";
                    age = 11;
                    favoriteThing = "learning";
                }
            },
            new Person() {
                {
                    id = 3;
                    name = "Ron Weasley";
                    age = 11;
                    favoriteThing = "chess";
                }
            }));

    // CREATE
    @POST
    public Response addPerson(Person person) {
        people.add(person);
        return Response.status(Response.Status.CREATED)
                .entity("Person '" + person.name + "' has been added.").build();
    }

    // READ
    @GET
    public Response getPeople() {
        return Response.ok(people).build();
    }

    // READ - get person by ID
    @GET
    @Path("/{id}")
    public Response getPersonById(@PathParam("id") int id) {
        for (Person person : people) {
            if (person.id == id) {
                return Response.ok(person).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Person with ID " + id + " not found.").build();
    }

    // UPDATE - change age
    @PATCH
    @Path("/{id}/age")
    public Response updatePersonAge(@PathParam("id") int id, int newAge) {
        for (Person person : people) {
            if (person.id == id) {
                person.age = newAge;
                return Response.ok("Person with ID " + id + " now has age " + newAge + ".").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Person with ID " + id + " not found.").build();
    }

    // UPDATE - change entire database entry
    @PUT
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") int id, Person updatedPerson) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).id == id) {
                people.set(i, updatedPerson);
                return Response.ok("Person with ID " + id + " has been updated.").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Person with ID " + id + " not found.").build();
    }

    // DELETE
    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") int id) {
        for (Person person : people) {
            if (person.id == id) {
                people.remove(person);
                return Response.ok("Person with ID " + id + " has been deleted.").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Person with ID " + id + " not found.").build();
    }
}
