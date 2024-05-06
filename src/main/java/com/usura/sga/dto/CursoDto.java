package com.usura.sga.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoDto {
    private int idCurso;
    private String nombreCurso;
    private String titulo;
    private int horas;
    private String registro_men;
}
