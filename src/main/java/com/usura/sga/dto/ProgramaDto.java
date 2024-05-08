package com.usura.sga.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaDto {
    private int  idPrograma;
    private String nombre;
    private String titulo;
    private int creditos;
    private int semestres;
    private String registroMen;
}
