package com.example.lab7_gr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = "document"))
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

    @NotBlank
    @Column(name = "document")
    @Pattern(regexp = "^[0-9]+$")
    private String document;

    @NotNull
    @Size(max=10)
    @Column(name = "document_type", length = 10)
    private DocumentType documentType;

    @AssertTrue(message = "El DNI debe tener 8 dígitos o el RUC 11 dígitos")
    public boolean validDocument() {
        if ("DNI".equalsIgnoreCase(documentType)) {
            return document.length() == 8;
        } else if ("RUC".equalsIgnoreCase(documentType)) {
            return document.length() == 11;
        }
        return false;
    }

}

