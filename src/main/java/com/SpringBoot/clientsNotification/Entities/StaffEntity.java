package com.SpringBoot.clientsNotification.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff_table")
public class StaffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long id;
    @Column(name = "staff_name", length = 100)
    private String name;
    @Column(name = "staff_email", unique = true, nullable = false, length = 100)
    private String email;
    private String password;
    @Column(name = "staff_role", nullable = false)
    private String role;
}
