package com.bryobone.graben.resources;

import com.bryobone.graben.core.User;
import com.bryobone.graben.core.UserParams;
import com.bryobone.graben.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by jarvis on 4/10/14.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
  private final UserDAO userDao;
  private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

  public UserResource(UserDAO userDao) { this.userDao = userDao; }

  @POST
  @UnitOfWork //Create a UserParams Mask for this information
  public User createUser(UserParams userParams) {
    LOGGER.info("User params: {}", userParams.getUsername());
    // Check password confirmation is equal to password
    // Validate user
    return userDao.create(new User(
      userParams.getUsername(),
      userParams.getEmail(),
      userParams.getPassword()
    ));
  }
}
