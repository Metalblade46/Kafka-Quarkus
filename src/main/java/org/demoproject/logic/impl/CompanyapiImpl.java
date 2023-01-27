package org.demoproject.logic.impl;

import org.demoproject.dataaccess.Entities.Company;
import org.demoproject.dataaccess.Repositories.CompanyRepository;
import org.demoproject.logic.api.Companyapi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("Companyapi")
public class CompanyapiImpl implements Companyapi {
    @Inject
    private CompanyRepository companyRepository;

    @Override
    public String addCompany(Company company) {
        if (companyRepository.existsById(company.getId()))
            return "Id exists already";
        else {
            companyRepository.save(company);
            return "Added successfully";
        }
    }

    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> getCompanyById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public String deleteCompany(long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return "Deleted Successfully";
        } else {
            return "No such Company";
        }
    }

    @Override
    public String updateCompany(Long id, Company companyR) {
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            if(id!= companyR.getId()){
                return "Id Mismatch";
            }
        Company company= byId.get();
//            if (companyR.getId() !=null)
//                company.setId(companyR.getId());
            if (companyR.getName() !=null)
                company.setName(companyR.getName());
            if (companyR.getSector() != null)
                company.setSector(companyR.getSector());
            if (companyR.getHeadquarter() != null)
                company.setHeadquarter(companyR.getHeadquarter());
            companyRepository.save(company);
            return "Updated Successfully";
        }
        else
            return "Id doesn't exist";

    }
}
