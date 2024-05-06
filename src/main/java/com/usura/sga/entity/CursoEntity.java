package com.usura.sga.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "curso")
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcurso;

    @Column(name = "nombre_curso")
    private String nombreCurso;

    private String titulo;

    private int horas;

    @Column(name = "registro_men")
    private String registroMen;
}
