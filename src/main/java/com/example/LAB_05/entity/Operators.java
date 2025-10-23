package com.example.LAB_05.entity;

import com.example.LAB_05.models.ApplicationRequest;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "operators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Operators {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String department;

    @ManyToMany(mappedBy = "operators")
    private List<ApplicationRequest> applicationRequests;
}
