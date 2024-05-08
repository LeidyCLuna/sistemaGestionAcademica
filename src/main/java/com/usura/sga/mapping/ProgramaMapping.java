package com.usura.sga.mapping;

import com.usura.sga.dto.ProgramaDto;
import com.usura.sga.entity.ProgramaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProgramaMapping {
    public ProgramaEntity programaDtoToProgramaEntity(
            ProgramaDto programaDto) {
        return ProgramaEntity.builder()
                .idprograma(programaDto.getIdPrograma())
                .nombre(programaDto.getNombre())
                .titulo(programaDto.getTitulo())
                .creditos(programaDto.getCreditos())
                .registroMen(programaDto.getRegistroMen())
                .semestres(programaDto.getSemestres())
                .build();
    }

    public ProgramaDto programaEntityToProgramaDTO(
            ProgramaEntity programaEntity) {
        return ProgramaDto.builder()
                .idPrograma(programaEntity.getIdprograma())
                .nombre(programaEntity.getNombre())
                .titulo(programaEntity.getTitulo())
                .creditos(programaEntity.getCreditos())
                .registroMen(programaEntity.getRegistroMen())
                .semestres(programaEntity.getSemestres())
                .build();
    }

}
