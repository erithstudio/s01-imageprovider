package com.erith.api.imageprocessing.health;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class AppHealthCheck extends HealthCheck
{
    @Override
    protected Result check() throws Exception
    {
        // if(Check some condition == true){
        return Result.healthy();
        // return Result.unhealthy("Error message");
    }
}