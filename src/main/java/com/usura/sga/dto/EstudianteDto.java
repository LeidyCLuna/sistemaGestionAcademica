package com.usura.sga.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDto {
    private int idEstudiante;
    private String tipoDocumento;
    private String documento;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String sexo;
    private MatriculaDto matriculaDto;
}
