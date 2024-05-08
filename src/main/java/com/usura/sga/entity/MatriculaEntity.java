package com.usura.sga.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "matricula")
public class MatriculaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMatricula;

    private int año;

    private int periodo;

    private int nivel;
    @Column(name = "fecha_matricula")
    private Date fechaMatricula;

    @ManyToOne
    @JoinColumn(name = "idprograma_curso")
    private ProgramaEntity idPrograma;

    @ManyToOne
    @JoinColumn(name = "idestudiante")
    private EstudianteEntity idEstudiante;
}
