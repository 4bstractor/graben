package com.bryobone.graben.auth;

import com.bryobone.graben.db.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class GrabenAuthenticator implements Authenticator<BasicCredentials, Boolean> {
  private final UserDAO userDao;

  public GrabenAuthenticator(UserDAO userDao) { this.userDao = userDao; }

  @Override
  public Optional<Boolean> authenticate(BasicCredentials credentials) throws AuthenticationException {
    boolean validUser = (userDao.checkUser(credentials.getUsername(), credentials.getPassword()) == 1);
    if (validUser) {
      return Optional.of(true);
    }
    return Optional.absent();
  }
}
