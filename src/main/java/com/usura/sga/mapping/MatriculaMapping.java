package com.usura.sga.mapping;

import com.usura.sga.dto.MatriculaDto;
import com.usura.sga.entity.MatriculaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MatriculaMapping {

    public MatriculaEntity matriculaDtoToMatriculaEntity(
            MatriculaDto matriculaDto) {
        return MatriculaEntity.builder()
                .idMatricula(matriculaDto.getIdMatricula())
                .idEstudiante(new EstudianteMapping().estudianteDtoToEstudianteEntity(matriculaDto.getIdEstudiante()))
                .idPrograma(new ProgramaMapping().programaDtoToProgramaEntity(matriculaDto.getIdPrograma()))
                .fechaMatricula(matriculaDto.getFechaMatricula())
                .año(matriculaDto.getAño())
                .nivel(matriculaDto.getNivel())
                .periodo(matriculaDto.getPeriodo())
                .build();
    }

    public MatriculaDto matriculaEntityToMatriculaDTO(
            MatriculaEntity matriculaEntity) {
        return MatriculaDto.builder()
                .idMatricula(matriculaEntity.getIdMatricula())
                .idEstudiante(new EstudianteMapping().estudianteEntityToEstudianteDTO(matriculaEntity.getIdEstudiante()))
                .idPrograma(new ProgramaMapping().programaEntityToProgramaDTO(matriculaEntity.getIdPrograma()))
                .fechaMatricula(matriculaEntity.getFechaMatricula())
                .año(matriculaEntity.getAño())
                .nivel(matriculaEntity.getNivel())
                .periodo(matriculaEntity.getPeriodo())
                .build();
    }
}
