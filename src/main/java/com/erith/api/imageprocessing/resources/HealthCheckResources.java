package com.erith.api.imageprocessing.resources;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Set;

@Produces(MediaType.APPLICATION_JSON)
@Path("/status")
public class HealthCheckResources {
    private HealthCheckRegistry registry;

    public HealthCheckResources(HealthCheckRegistry registry) {
        this.registry = registry;
    }

    @GET
    public Set<Map.Entry<String, HealthCheck.Result>> getStatus() {
        return registry.runHealthChecks().entrySet();
    }
}