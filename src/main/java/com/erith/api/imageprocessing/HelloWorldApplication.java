package com.erith.api.imageprocessing;

import com.erith.api.imageprocessing.configuration.HelloWorldConfiguration;
import com.erith.api.imageprocessing.resource.ClientResource;
import com.erith.api.imageprocessing.resource.FileResource;
import com.erith.api.imageprocessing.resource.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(new ViewBundle<HelloWorldConfiguration>());
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);
        environment.jersey().register(FileResource.class);
        environment.jersey().register(ClientResource.class);

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }

}