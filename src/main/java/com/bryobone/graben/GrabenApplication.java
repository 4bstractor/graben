package com.bryobone.graben;

import com.bryobone.graben.auth.ExampleAuthenticator;
import com.bryobone.graben.cli.RenderCommand;
import com.bryobone.graben.core.Person;
import com.bryobone.graben.core.Template;
import com.bryobone.graben.db.PersonDAO;
import com.bryobone.graben.health.TemplateHealthCheck;
import com.bryobone.graben.resources.*;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class GrabenApplication extends Application<GrabenConfiguration> {
    public static void main(String[] args) throws Exception {
        new GrabenApplication().run(args);
    }

    private final HibernateBundle<GrabenConfiguration> hibernateBundle =
            new HibernateBundle<GrabenConfiguration>(Person.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(GrabenConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "graben-core";
    }

    @Override
    public void initialize(Bootstrap<GrabenConfiguration> bootstrap) {
        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<GrabenConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(GrabenConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(GrabenConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
        final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());
        final Template template = configuration.buildTemplate();

        environment.healthChecks().register("template", new TemplateHealthCheck(template));

        environment.jersey().register(new BasicAuthProvider<>(new ExampleAuthenticator(),
                                                              "SUPER SECRET STUFF"));
        environment.jersey().register(new HelloWorldResource(template));
        environment.jersey().register(new ViewResource());
        environment.jersey().register(new ProtectedResource());
        environment.jersey().register(new PeopleResource(dao));
        environment.jersey().register(new PersonResource(dao));
    }
}