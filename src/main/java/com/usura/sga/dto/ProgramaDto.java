package com.usura.sga.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaDto {
    private int  idPrograma;
    private String nombrePrograma;
    private String titulo;
    private int cretidos;
    private int semestres;
    private String registroMen;
}
