package com.church.church_offerings_management_system;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "Parish")
@Getter
@Setter
public class Parish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int parishNumber;
    
    private String name;
}
