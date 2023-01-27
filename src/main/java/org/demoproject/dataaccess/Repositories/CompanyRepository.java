package org.demoproject.dataaccess.Repositories;

import org.demoproject.dataaccess.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
