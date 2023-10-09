package com.SpringBoot.clientsNotification.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients_table")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;
    @Column(name = "client_name", length = 100)
    private String name;
    @Column(name = "client_email", nullable = false, unique = true, length = 100)
    private String email;
    @Column(name = "client_phone", nullable = false, unique = true)
    private String phone;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity product;
}
