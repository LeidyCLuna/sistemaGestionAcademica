package com.usura.sga.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "programa_curso")
public class ProgramaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprograma_curso")
    private int  idprograma;

    private String nombre;

    private String titulo;

    private int creditos;

    private int semestres;

    @Column(name = "registro_men")
    private String registroMen;
}
