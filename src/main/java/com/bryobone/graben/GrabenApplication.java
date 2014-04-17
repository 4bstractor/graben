package com.bryobone.graben;

import com.bryobone.graben.auth.GrabenAuthenticator;
import com.bryobone.graben.cli.RenderCommand;
import com.bryobone.graben.core.Person;
import com.bryobone.graben.core.Template;
import com.bryobone.graben.core.User;
import com.bryobone.graben.db.PersonDAO;
import com.bryobone.graben.db.UserDAO;
import com.bryobone.graben.health.TemplateHealthCheck;
import com.bryobone.graben.resources.PeopleResource;
import com.bryobone.graben.resources.PersonResource;
import com.bryobone.graben.resources.UserResource;
import com.google.common.cache.CacheBuilderSpec;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class GrabenApplication extends Application<GrabenConfiguration> {
  public static void main(String[] args) throws Exception {
      new GrabenApplication().run(args);
  }

  private final HibernateBundle<GrabenConfiguration> userHibernateBundle =
          new HibernateBundle<GrabenConfiguration>(User.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(GrabenConfiguration configuration) {
              return configuration.getDataSourceFactory();
            }
          };

  private final HibernateBundle<GrabenConfiguration> personHibernateBundle =
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
    bootstrap.addBundle(personHibernateBundle);
    bootstrap.addBundle(userHibernateBundle);
  }

  @Override
  public void run(GrabenConfiguration configuration, Environment environment) throws ClassNotFoundException {
    final PersonDAO personDao = new PersonDAO(personHibernateBundle.getSessionFactory());
    final UserDAO userDao = new UserDAO(userHibernateBundle.getSessionFactory());
    final Template template = configuration.buildTemplate();

    environment.healthChecks().register("template", new TemplateHealthCheck(template));

    environment.jersey().register(new PeopleResource(personDao));
    environment.jersey().register(new PersonResource(personDao));
    environment.jersey().register(new UserResource(userDao));

    CachingAuthenticator<BasicCredentials, User> authenticator = new CachingAuthenticator<BasicCredentials, User>(
      environment.metrics(),
      new GrabenAuthenticator(userDao),
      CacheBuilderSpec.parse(configuration.getCacheBuilderArgs())
    );

    environment.jersey().register(new BasicAuthProvider<User>(authenticator, "Web Service Realm"));
  }
}
