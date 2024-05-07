package com.usura.sga.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "programa")
public class ProgramaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  idprograma;

    private String nombre;

    private String titulo;

    private int cretidos;

    private int semestres;

    @Column(name = "registro_men")
    private String registroMen;
}
