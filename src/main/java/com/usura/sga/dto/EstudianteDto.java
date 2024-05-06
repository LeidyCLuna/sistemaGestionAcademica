package com.usura.sga.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDto {
    private int idEstudiante;
    private String nombresEstudiante;
    private String apellidosEstudiante;
    private String direccion;
    private String telefono;
    private String sexo;
}
