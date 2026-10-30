package com.church.church_offerings_management_system;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pledge")
@Getter
@Setter
public class Pledge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate pledgeDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PledgeStatus status;

    private double amount;

    private String purpose;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "treasurer_id", nullable = false)
    private Treasurer treasurer;
}
