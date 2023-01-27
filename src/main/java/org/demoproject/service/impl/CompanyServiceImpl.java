package org.demoproject.service.impl;

import io.smallrye.reactive.messaging.kafka.Record;
import org.demoproject.dataaccess.Entities.Company;
import org.demoproject.logic.api.Companyapi;
import org.demoproject.logic.kafka.CompanyConsumer;
import org.demoproject.logic.kafka.CompanyProducer;
import org.demoproject.service.api.CompanyService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("CompanyService")
public class CompanyServiceImpl implements CompanyService {
    @Inject
    Companyapi companyapi;
    @Inject
    CompanyProducer companyProducer;
    @Inject
    CompanyConsumer companyConsumer;
    @Override
    public Response addCompany(Company company) {
        if(company.getId()==null){
            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }
        companyProducer.sendKafkaCompany(company);
        Company companyR = companyConsumer.recieveKafkaCompany(Record.of(company.getId(), company));
            if(companyapi.addCompany(companyR).equals("Id exists already")){
                companyapi.updateCompany(companyR.getId(),companyR);
                return Response.ok("Updated company successfully!").build();
            };
            return Response.ok("Added company successfully").build();
    }

    @Override
    public Response getCompanies() {
        List<Company> companies = companyapi.getCompanies();
        if(companies.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).entity("No Company exists in Repository").build();
        else
            return Response.ok(companies).build();
    }

    @Override
    public Response getCompanyById(long id) {
        Optional<Company> companyById = companyapi.getCompanyById(id);
        if(!companyById.isPresent())
            return Response.status(Response.Status.NOT_FOUND).entity("Company with id "+id+" doesn't exist").build();
        else
            return Response.ok(companyById.get()).build();

    }
    @Override
    public Response updateCompany(long id,Company company) {
        String s = companyapi.updateCompany(id, company);
        if(s.equals("Id doesn't exist"))
            return Response.status(Response.Status.NOT_MODIFIED).entity(s).build();
        else if(s.equals("Id Mismatch")){
            return Response.status(Response.Status.CONFLICT).entity(s).build();
        }
            return Response.accepted(s).build();
    }

    @Override
    public Response deleteCompany(long id) {
        String s = companyapi.deleteCompany(id);
        if(s.equals("No such Company"))
            return Response.notModified().entity(s).build();
        else
            return Response.ok(s).build();
    }
}
