package org.acme.Goals;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/goals")
@Produces(MediaType.APPLICATION_JSON)
public class GoalResource {

    // GET list of goals
    @GET
    public Response getGoals() {
        return Response.ok(Goal.listAll()).build();
    }

    // POST create a new goal
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGoal(Goal goal) {
        goal.persist();
        return Response.status(Response.Status.CREATED)
                .entity(goal).build();
    }

    // GET goal by ID
    @GET
    @Path("/{id}")
    public Response getGoalById(@PathParam("id") Long id) {
        Goal goal = Goal.findById(id);
        if (goal != null) {
            return Response.ok(goal).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Goal with ID " + id + " not found.")
                    .build();
        }
    }

    // PATCH - update goal by ID
    @PATCH
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGoal(@PathParam("id") Long id, Goal updatedGoal) {
        Goal goal = Goal.findById(id);
        if (goal == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Goal with ID " + id + " not found.")
                    .build();
        }

        // Only update fields that are provided (PATCH behavior)
        if (updatedGoal.getGoalName() != null) {
            goal.setGoalName(updatedGoal.getGoalName());
        }

        if (updatedGoal.getGoalAmount() != null) {
            goal.setGoalAmount(updatedGoal.getGoalAmount());
        }

        if (updatedGoal.getTargetDate() != null) {
            goal.setTargetDate(updatedGoal.getTargetDate());
        }

        return Response.ok(goal).build();
    }

    // PATCH - update amount saved for a goal by ID
    @PATCH
    @Path("/{id}/amountSaved")
    @Transactional
    public Response updatedAmountSaved(@PathParam("id") Long id, Goal updatedGoal) {
        Goal goal = Goal.findById(id);
        if (goal == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Goal with ID " + id + " not found.")
                    .build();
        }

        if (updatedGoal.getAmountSaved() != null) {
            goal.setAmountSaved(updatedGoal.getAmountSaved());
        }

        return Response.ok(goal).build();
    }

    // DELETE goal by ID
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteGoal(@PathParam("id") Long id) {
        Goal goal = Goal.findById(id);
        if (goal == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Goal with ID " + id + " not found.")
                    .build();
        }
        goal.delete();
        return Response.ok("Goal with ID " + id + " deleted.").build();
    }
}
