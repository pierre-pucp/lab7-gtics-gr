package com.example.lab7_gr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {

    public enum DocumentType { DNI, RUC }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Size(max=100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max=11)
    @Column(name = "document", length = 11)
    private String document;

    @Size(max=10)
    @Column(name = "document_type", length = 10)
    private DocumentType documentType;

}

