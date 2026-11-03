package com.example.lab7_gr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer") // ", uniqueConstraints = @UniqueConstraint(columnNames = "document")"
public class Customer {

    public enum DocumentType { DNI, RUC }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max=100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "El documento es obligatorio")
    @Column(name = "document", unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]+$", message = "El documento solo debe contener números")
    private String document;

    @NotNull(message = "Debe seleccionar un tipo de documento")
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @AssertTrue(message = "El DNI debe tener 8 dígitos o el RUC 11 dígitos")
    public boolean isValidDocument() {
        if (documentType == null || document == null) {
            return true;
        }
        if (documentType == DocumentType.DNI) {
            return document.length() == 8;
        } else if (documentType == DocumentType.RUC) {
            return document.length() == 11;
        }
        return false;
    }
}

