package com.usura.sga.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "estudiante")
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idestudiante;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    private String documento;

    private String nombres;

    private String apellidos;

    private String direccion;

    private String telefono;

    private String sexo;
}
