package com.bryobone.graben.resources;

import com.bryobone.graben.db.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by jarvis on 5/20/14.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionResource {
    private final UserDAO userDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionResource.class);

    public SessionResource(UserDAO userDao) { this.userDao = userDao; }
    // Given credentials (api_key, login info) check it and return an access token
}
