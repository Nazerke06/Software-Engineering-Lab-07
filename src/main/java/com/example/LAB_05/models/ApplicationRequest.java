package com.example.LAB_05.models;


import com.example.LAB_05.entity.Courses;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;



@Entity
@Table(name = "ApplicationList")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;
    @Column(name = "Name", length = 200)
    private String userName;
    @Column(name = "Course Name", length = 200)
    private String courseName;
    @Column(name = "Commentary", length = 200)
    private String commentary;
    @Column(name = "Phone", length = 200)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    private Courses course;

    private boolean handled;
}
