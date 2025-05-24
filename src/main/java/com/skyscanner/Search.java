package com.skyscanner;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class Search {

    private final List<SearchResult> results;

    public Search(List<SearchResult> results) {
        this.results = results;
    }

    @GET
    public Response search(@QueryParam("city") String city) {
        if (city == null || city.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parameter 'city' is required").build();
        }

        List<SearchResult> filteredResults = results.stream()
                .filter(r -> r.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());

        return Response.ok(filteredResults).build();
    }
}
