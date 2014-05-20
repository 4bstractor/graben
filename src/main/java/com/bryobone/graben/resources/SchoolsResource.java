package com.bryobone.graben.resources;

import com.bryobone.graben.core.School;
import com.bryobone.graben.core.User;
import com.bryobone.graben.db.SchoolDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/schools")
@Produces(MediaType.APPLICATION_JSON)
public class SchoolsResource {

    private final SchoolDAO schoolDAO;

    public SchoolsResource(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    @POST
    @UnitOfWork
    public School createPerson(School person) {
        return schoolDAO.create(person);
    }

    @GET
    @UnitOfWork
    public List<School> listPeople(@Auth User user) {
        return schoolDAO.findAll();
    }

}
