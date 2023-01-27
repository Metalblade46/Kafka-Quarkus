package org.demoproject.service.api;

import org.demoproject.dataaccess.Entities.Company;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("company")
@Transactional
public interface CompanyService {
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    Response addCompany(Company company);
    @GET
    @Path("/companies")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCompanies();
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCompanyById(@PathParam("id") long id);
    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response updateCompany(long id, Company company);

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteCompany(@PathParam("id") long id);



}
