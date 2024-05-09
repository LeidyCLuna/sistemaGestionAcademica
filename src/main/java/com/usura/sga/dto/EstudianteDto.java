package com.usura.sga.dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDto {
    private int idEstudiante;
    private String tipoDocumento;
    @NotBlank(message = "El documento del estudiante no puede estar vacío")
    private String documento;
    @NotBlank(message = "El nombre del estudiante no puede estar vacío")
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String sexo;
    private MatriculaDto matriculaDto;
}
