package com.usura.sga.service.impl;

import com.usura.sga.dto.MatriculaDto;
import com.usura.sga.entity.MatriculaEntity;
import com.usura.sga.mapping.MatriculaMapping;
import com.usura.sga.repository.IMatriculaRepository;
import com.usura.sga.service.IMatriculaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MatriculaServiceImpl implements IMatriculaService {

    private final IMatriculaRepository iMatriculaRepository;

    @Override
    public MatriculaDto crearMatricula(MatriculaDto matriculaDto) {
        if (matriculaDto != null) {
            MatriculaEntity crearMatricula = new MatriculaMapping().matriculaDtoToMatriculaEntity(matriculaDto);
            iMatriculaRepository.saveAndFlush(crearMatricula);
            return new MatriculaMapping().matriculaEntityToMatriculaDTO(crearMatricula);
        } else {
            return null;
        }
    }

    @Override
    public MatriculaDto buscarMatricula(Integer idMatricula) {
        MatriculaEntity matriculaEntity = iMatriculaRepository.findByIdMatricula(idMatricula);
        if (matriculaEntity != null) {
            return new MatriculaMapping().matriculaEntityToMatriculaDTO(matriculaEntity);
        } else {
            return null;
        }
    }

}
