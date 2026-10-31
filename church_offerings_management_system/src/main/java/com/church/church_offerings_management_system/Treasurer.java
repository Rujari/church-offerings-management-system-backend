package com.church.church_offerings_management_system;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "treasurer")
@Getter
@Setter
public class Treasurer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    
    private String lastName;
    
    private String phone;

    @ManyToOne
    @JoinColumn(name = "parish_id", nullable = true)
    private Parish parish;

    @ManyToMany
    @JoinTable(
        name = "treasurer_fund_type",
        joinColumns = @JoinColumn(name = "treasurer_id"),
        inverseJoinColumns = @JoinColumn(name = "fund_type_id")
    )
    private List<FundType> fundTypes;
}
