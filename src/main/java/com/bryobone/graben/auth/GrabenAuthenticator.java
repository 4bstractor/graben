package com.bryobone.graben.auth;

import com.bryobone.graben.core.User;
import com.bryobone.graben.db.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrabenAuthenticator implements Authenticator<BasicCredentials, User> {
  private final UserDAO userDao;
  private static final Logger LOGGER = LoggerFactory.getLogger(GrabenAuthenticator.class);

  public GrabenAuthenticator(UserDAO userDao) { this.userDao = userDao; }

  @Override
  public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
    User validUser = userDao.findByUsername(credentials.getUsername());
    LOGGER.info("User {} Logged Id", validUser.getEmail());
    if (validUser.getPassword().equals(credentials.getPassword())) {
      return Optional.fromNullable(validUser);
    }
    return Optional.absent();
  }
}
