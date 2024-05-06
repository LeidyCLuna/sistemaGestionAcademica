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

    @Column(name = "nombres_estudiante")
    private String nombresEstudiante;

    @Column(name = "apellidos_estudiante")
    private String apellidosEstudiante;

    private String direccion;

    private String telefono;

    private String sexo;
}
