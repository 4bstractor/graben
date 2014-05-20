package com.bryobone.graben.resources;

import com.bryobone.graben.core.School;
import com.bryobone.graben.db.SchoolDAO;
import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/schools/{schoolId}")
@Produces(MediaType.APPLICATION_JSON)
public class SchoolResource {

  private final SchoolDAO schoolDAO;

  public SchoolResource(SchoolDAO schoolDAO) {
      this.schoolDAO = schoolDAO;
  }

  @GET
  @UnitOfWork
  public School getSchool(@PathParam("schoolId") LongParam schoolId) {
      return findSafely(schoolId.get());
  }

	private School findSafely(long schoolId) {
	    final Optional<School> school = schoolDAO.findById(schoolId);
        if (!school.isPresent()) {
            throw new NotFoundException("No such user.");
        }
		return school.get();
	}
}
