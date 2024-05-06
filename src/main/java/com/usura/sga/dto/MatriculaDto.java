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
    private Date fechaMatricula;
    private int codigoPrograma;
    private int codigoCurso;
    private int estudianteCurso;
}
