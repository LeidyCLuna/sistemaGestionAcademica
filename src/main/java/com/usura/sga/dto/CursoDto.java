package com.usura.sga.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoDto {
    private int idCurso;
    private String nombre;
    private String titulo;
    private int horas;
    private String registroMen;
}
