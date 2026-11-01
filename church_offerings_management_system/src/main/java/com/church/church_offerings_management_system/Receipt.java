package com.church.church_offerings_management_system;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "receipt")
@Getter
@Setter
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fundName;

    private String remarks;

    private int amount;

    private LocalDate issuedDate;

    @ManyToOne
    @JoinColumn(name = "pledge_id", nullable = false)
    private Pledge pledge;
}
