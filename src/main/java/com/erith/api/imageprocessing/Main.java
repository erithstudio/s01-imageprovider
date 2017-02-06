package com.erith.api.imageprocessing;

import com.erith.api.imageprocessing.config.AppConfiguration;
import com.erith.api.imageprocessing.health.AppHealthCheck;
import com.erith.api.imageprocessing.resources.FileResources;
import com.erith.api.imageprocessing.resources.HealthCheckResources;
import com.example.helloworld.HelloWorldConfiguration;
import com.example.helloworld.cli.RenderCommand;
import com.example.helloworld.core.Person;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.util.Map;

public class Main extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    private final HibernateBundle<AppConfiguration> hibernateBundle =
            new HibernateBundle<AppConfiguration>(Person.class) {
                public PooledDataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
            public PooledDataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle<AppConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(AppConfiguration configuration) {
                return configuration.getViewRendererConfiguration();
            }
        });
    }

    @Override
    public void run(AppConfiguration appConfiguration, Environment e) throws Exception {
        //Run multiple health checks
        e.healthChecks().register("APIHealthCheck", new AppHealthCheck());
        e.jersey().register(new HealthCheckResources(e.healthChecks()));
        e.jersey().register(new FileResources());
    }
}
