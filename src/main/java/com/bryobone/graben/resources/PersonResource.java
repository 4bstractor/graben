package com.bryobone.graben.resources;

import com.bryobone.graben.core.Person;
import com.bryobone.graben.db.PersonDAO;
import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/people/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

  private final PersonDAO peopleDAO;

  public PersonResource(PersonDAO peopleDAO) {
      this.peopleDAO = peopleDAO;
  }

  @GET
  @UnitOfWork
  public Person getPerson(@PathParam("personId") LongParam personId) {
      return findSafely(personId.get());
  }

	private Person findSafely(long personId) {
		final Optional<Person> person = peopleDAO.findById(personId);
      if (!person.isPresent()) {
        throw new NotFoundException("No such user.");
      }
		return person.get();
	}
}
