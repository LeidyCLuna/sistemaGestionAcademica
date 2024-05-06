package com.usura.sga.mapping;

import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.entity.EstudianteEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EstudianteMapping {

    public EstudianteEntity estudianteDtoToEstudianteEntity(
            EstudianteDto estudianteDto) {
        return EstudianteEntity.builder()
                .idestudiante(estudianteDto.getIdEstudiante())
                .nombresEstudiante(estudianteDto.getNombresEstudiante())
                .apellidosEstudiante(estudianteDto.getApellidosEstudiante())
                .direccion(estudianteDto.getDireccion())
                .telefono(estudianteDto.getTelefono())
                .sexo(estudianteDto.getSexo())
                .build();
    }

    public EstudianteDto estudianteEntityToEstudianteDTO(
            EstudianteEntity estudianteEntity) {
        return EstudianteDto.builder()
                .idEstudiante(estudianteEntity.getIdestudiante())
                .nombresEstudiante(estudianteEntity.getNombresEstudiante())
                .apellidosEstudiante(estudianteEntity.getApellidosEstudiante())
                .direccion(estudianteEntity.getDireccion())
                .telefono(estudianteEntity.getTelefono())
                .sexo(estudianteEntity.getSexo())
                .build();
    }
}
