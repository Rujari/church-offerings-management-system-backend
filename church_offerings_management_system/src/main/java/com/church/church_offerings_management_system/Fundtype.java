package com.church.church_offerings_management_system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fund_type")
@Getter
@Setter
public class FundType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "fundtypes")
    @JsonIgnore
    private List<Treasurer> treasurers;
}
