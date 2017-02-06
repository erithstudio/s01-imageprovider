package com.example.simple;

import com.example.simple.configuration.ImageAPIConfiguration;
import com.example.simple.health.TemplateHealthCheck;
import com.example.simple.resources.ClientResource;
import com.example.simple.resources.FileResource;
import com.example.simple.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class MainApp extends Application<ImageAPIConfiguration> {
    public static void main(String[] args) throws Exception {
        new MainApp().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ImageAPIConfiguration> bootstrap) {
        // nothing to do yet
        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(new ViewBundle<ImageAPIConfiguration>());
    }

    @Override
    public void run(ImageAPIConfiguration configuration, Environment environment) {
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