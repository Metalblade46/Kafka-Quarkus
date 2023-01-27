package org.demoproject.dataaccess.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String sector;
    private String headquarter;

}
