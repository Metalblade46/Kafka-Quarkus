package org.demoproject.service.api;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.demoproject.dataaccess.Entities.Company;
import org.demoproject.dataaccess.Repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
class CompanyServiceTest {
    @InjectMock
    CompanyRepository companyRepository;
    @Inject
    CompanyService companyService;

    private Company company;
    @BeforeEach
    void setUp() {
        company=new Company();
        company.setId(1l);
        company.setName("CG");
        company.setSector("IT");
        company.setHeadquarter("Paris");
    }

    @Test
    void addCompanyOKAdded() {
        Mockito.when(companyRepository.save(company)).thenReturn(company);
        Company newCompany = new Company();
        newCompany.setId(2l);
        newCompany.setName("KN");
        newCompany.setSector("Logistics");
        newCompany.setHeadquarter("Hamburg");
        Response response = companyService.addCompany(newCompany);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Added company successfully",response.getEntity());
    }
    @Test
    void addCompanyOKUpdated() {
        Mockito.when(companyRepository.existsById(1L)).thenReturn(true);
        Company newCompany = new Company();
        newCompany.setId(1l);
        newCompany.setName("KN");
        newCompany.setSector("Logistics");
        newCompany.setHeadquarter("Hamburg");
        Response response = companyService.addCompany(newCompany);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Updated company successfully!",response.getEntity());
    }
    @Test
    void addCompanyKO() {
        Mockito.when(companyRepository.save(company)).thenReturn(company);
        Company newCompany = new Company();
        newCompany.setId(null);
        newCompany.setName("KN");
        newCompany.setSector("Logistics");
        newCompany.setHeadquarter("Hamburg");
        Response response = companyService.addCompany(newCompany);
        assertNotNull(response);
        assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(),response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    void getCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        Mockito.when(companyRepository.findAll()).thenReturn(companies);
        Response response = companyService.getCompanies();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
        assertNotNull(response.getEntity());
        List<Company> entity = (List<Company>)response.getEntity();
        assertFalse(entity.isEmpty());
        assertEquals(1l,entity.get(0).getId());
        assertEquals("CG",entity.get(0).getName());
        assertEquals("IT",entity.get(0).getSector());
        assertEquals("Paris",entity.get(0).getHeadquarter());
    }

    @Test
    void getCompanyByIdOK() {
        Mockito.when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        Response response = companyService.getCompanyById(1l);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
        assertNotNull(response.getEntity());
        Company entity = (Company)response.getEntity();
        assertEquals(1l,entity.getId());
        assertEquals("CG",entity.getName());
        assertEquals("IT",entity.getSector());
        assertEquals("Paris",entity.getHeadquarter());

    }
    @Test
    void getCompanyByIdKO(){
        Mockito.when(companyRepository.findById(1L)).thenReturn(Optional.empty());
        Response response = companyService.getCompanyById(1l);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus());
        assertEquals("Company with id 1 doesn't exist",response.getEntity());
    }

    @Test
    void updateCompanyOK() {
        Mockito.when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        Company newCompany = new Company();
        newCompany.setId(1l);
        newCompany.setName("KN");
        newCompany.setSector("Logistics");
        newCompany.setHeadquarter("Hamburg");
        Response response = companyService.updateCompany(1l, newCompany);
        assertNotNull(response);
        assertEquals(Response.Status.ACCEPTED.getStatusCode(),response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Updated Successfully",response.getEntity());
    }
    @Test
    void updateCompanyKOIdNonExistent() {
        Mockito.when(companyRepository.findById(1L)).thenReturn(Optional.empty());
        Company newCompany = new Company();
        newCompany.setId(1l);
        newCompany.setName("KN");
        newCompany.setSector("Logistics");
        newCompany.setHeadquarter("Hamburg");
        Response response = companyService.updateCompany(1l, newCompany);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_MODIFIED.getStatusCode(),response.getStatus());
        assertEquals("Id doesn't exist",response.getEntity());
    }
    @Test
    void updateCompanyKOIdMismatch() {
        Mockito.when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        Company newCompany = new Company();
        newCompany.setId(2l);
        newCompany.setName("KN");
        newCompany.setSector("Logistics");
        newCompany.setHeadquarter("Hamburg");
        Response response = companyService.updateCompany(1l, newCompany);
        assertNotNull(response);
        assertEquals(Response.Status.CONFLICT.getStatusCode(),response.getStatus());
        assertEquals("Id Mismatch",response.getEntity());
    }

    @Test
    void deleteCompanyOK() {
        Mockito.when(companyRepository.existsById(1l)).thenReturn(true);
        Response response = companyService.deleteCompany(1l);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Deleted Successfully",response.getEntity());
    }
    @Test
    void deleteCompanyKO() {
        Mockito.when(companyRepository.existsById(1l)).thenReturn(false);
        Response response = companyService.deleteCompany(1l);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_MODIFIED.getStatusCode(),response.getStatus());
        assertEquals("No such Company",response.getEntity());
    }
}