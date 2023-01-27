package org.demoproject.logic.api;

import org.demoproject.dataaccess.Entities.Company;

import java.util.List;
import java.util.Optional;

public interface Companyapi {
    String addCompany(Company company);
    //get companies
    List<Company> getCompanies();
    //get Company by ID
    Optional<Company> getCompanyById(long id);
    //delete Company
    String deleteCompany(long id);

    String updateCompany(Long id, Company companyR);
}
