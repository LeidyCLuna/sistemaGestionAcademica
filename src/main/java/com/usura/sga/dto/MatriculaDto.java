package com.usura.sga.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDto {
    private int idMatricula;
    private int año;
    private int periodo;
    private int nivel;
    private String fechaMatricula;
    private ProgramaDto idPrograma;
    private EstudianteDto idEstudiante;
}
