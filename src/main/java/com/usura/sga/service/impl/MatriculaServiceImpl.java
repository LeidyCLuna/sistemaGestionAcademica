package com.usura.sga.service.impl;

import com.usura.sga.dto.MatriculaDto;
import com.usura.sga.entity.MatriculaEntity;
import com.usura.sga.mapping.MatriculaMapping;
import com.usura.sga.repository.IMatriculaRepository;
import com.usura.sga.service.IMatriculaService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class MatriculaServiceImpl implements IMatriculaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatriculaServiceImpl.class);

    private final IMatriculaRepository iMatriculaRepository;

    @Override
    public MatriculaDto crearMatricula(MatriculaDto matriculaDto) {
        try {
            if (matriculaDto == null) {
                throw new IllegalArgumentException("La matrícula es nula.");
            }
            MatriculaEntity crearMatricula = new MatriculaMapping().matriculaDtoToMatriculaEntity(matriculaDto);
            iMatriculaRepository.saveAndFlush(crearMatricula);
            return new MatriculaMapping().matriculaEntityToMatriculaDTO(crearMatricula);
        } catch (DataAccessException e) {
            LOGGER.error("Error al crear la matrícula debido a un problema de acceso a la base de datos.", e);
            throw new RuntimeException("Ocurrió un error al crear la matrícula debido a un problema de acceso a la base de datos");
        } catch (Exception e) {
            LOGGER.error("Error al crear la matrícula.", e);
            throw new RuntimeException("Ocurrió un error al crear la matrícula");
        }
    }

    @Override
    public MatriculaDto buscarMatricula(Integer idMatricula) {
        try {
            MatriculaEntity matriculaEntity = iMatriculaRepository.findByIdMatricula(idMatricula);
            if (matriculaEntity != null) return new MatriculaMapping().matriculaEntityToMatriculaDTO(matriculaEntity);

            LOGGER.warn("No se encontró la matrícula con el ID: {}", idMatricula);
            return null;

        } catch (DataAccessException e) {
            LOGGER.error("Error de acceso a la base de datos al buscar la matrícula.", e);
            throw new RuntimeException("Error de acceso a la base de datos al buscar la matrícula");
        } catch (Exception e) {
            LOGGER.error("Error al buscar matrícula.", e);
            throw new RuntimeException("Ocurrió un error al buscar la matricula");
        }
    }

}

