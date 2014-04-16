package com.bryobone.graben;

import com.bryobone.graben.auth.GrabenAuthenticator;
import com.bryobone.graben.cli.RenderCommand;
import com.bryobone.graben.core.Person;
import com.bryobone.graben.core.Template;
import com.bryobone.graben.core.User;
import com.bryobone.graben.db.PersonDAO;
import com.bryobone.graben.db.UserDAO;
import com.bryobone.graben.health.TemplateHealthCheck;
import com.bryobone.graben.resources.*;
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
import io.dropwizard.views.ViewBundle;

public class GrabenApplication extends Application<GrabenConfiguration> {
  public static void main(String[] args) throws Exception {
      new GrabenApplication().run(args);
  }

  private final HibernateBundle<GrabenConfiguration> personHibernateBundle =
          new HibernateBundle<GrabenConfiguration>(Person.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(GrabenConfiguration configuration) {
              return configuration.getDataSourceFactory();
            }
          };

  private final HibernateBundle<GrabenConfiguration> userHibernateBundle =
          new HibernateBundle<GrabenConfiguration>(User.class) {
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
    bootstrap.addBundle(new ViewBundle());
  }

  @Override
  public void run(GrabenConfiguration configuration, Environment environment) throws ClassNotFoundException {
    final PersonDAO dao = new PersonDAO(personHibernateBundle.getSessionFactory());
    final UserDAO userDao = new UserDAO(userHibernateBundle.getSessionFactory());
    final Template template = configuration.buildTemplate();

    environment.healthChecks().register("template", new TemplateHealthCheck(template));

    environment.jersey().register(new HelloWorldResource(template));
    environment.jersey().register(new ViewResource());
    environment.jersey().register(new ProtectedResource());
    environment.jersey().register(new PeopleResource(dao));
    environment.jersey().register(new PersonResource(dao));

    CachingAuthenticator<BasicCredentials, Boolean> authenticator = new CachingAuthenticator<BasicCredentials, Boolean>(
      environment.metrics(),
      new GrabenAuthenticator(userDao),
      CacheBuilderSpec.parse(configuration.getCacheBuilderArgs())
    );

    environment.jersey().register(new BasicAuthProvider<Boolean>(authenticator, "Web Service Realm"));
  }
}
