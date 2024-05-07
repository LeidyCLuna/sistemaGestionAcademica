package com.usura.sga.mapping;

import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.entity.EstudianteEntity;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class EstudianteMapping {

    public EstudianteEntity estudianteDtoToEstudianteEntity(
            EstudianteDto estudianteDto) {
        return EstudianteEntity.builder()
                .idestudiante(estudianteDto.getIdEstudiante())
                .tipoDocumento(estudianteDto.getTipoDocumento())
                .documento(estudianteDto.getDocumento())
                .nombres(estudianteDto.getNombres())
                .apellidos(estudianteDto.getApellidos())
                .direccion(estudianteDto.getDireccion())
                .telefono(estudianteDto.getTelefono())
                .sexo(estudianteDto.getSexo())
                .build();
    }

    public EstudianteDto estudianteEntityToEstudianteDTO(
            EstudianteEntity estudianteEntity) {
        return EstudianteDto.builder()
                .idEstudiante(estudianteEntity.getIdestudiante())
                .tipoDocumento(estudianteEntity.getTipoDocumento())
                .documento(estudianteEntity.getDocumento())
                .nombres(estudianteEntity.getNombres())
                .apellidos(estudianteEntity.getApellidos())
                .direccion(estudianteEntity.getDireccion())
                .telefono(estudianteEntity.getTelefono())
                .sexo(estudianteEntity.getSexo())
                .build();
    }
}
