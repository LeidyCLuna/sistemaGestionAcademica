package com.usura.sga.service.impl;

import com.usura.sga.Exception.FinalizacionProcesoExcepcion;
import com.usura.sga.Exception.RelanzarProcesoExcepcion;
import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.dto.MatriculaDto;
import com.usura.sga.entity.EstudianteEntity;
import com.usura.sga.mapping.EstudianteMapping;
import com.usura.sga.repository.IEstudianteRepository;
import com.usura.sga.service.IEstudianteService;
import com.usura.sga.service.IMatriculaService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;


@Service
@AllArgsConstructor
public class EstudianteServiceimpl implements IEstudianteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstudianteServiceimpl.class);

    private final IEstudianteRepository iEstudianteRepository;
    private final IMatriculaService iMatriculaService;

    @Override
    public EstudianteDto crearEstudiante(EstudianteDto estudianteDto) {
        try {
            if (buscarEstudiante(estudianteDto.getDocumento()) != null) {
                LOGGER.info("El estudiante ya existe en la base de datos.");
                return null;
            }


            EstudianteEntity crearEstudiante = new EstudianteMapping().estudianteDtoToEstudianteEntity(estudianteDto);
            iEstudianteRepository.saveAndFlush(crearEstudiante);

            if (estudianteDto.getMatriculaDto() != null) {
                LOGGER.info("Se cargan datos de matricula, e informacion del estudiante.");
                datosMatricula(estudianteDto);
            } else LOGGER.info("No se cargan datos de matricula, por tal motivo solo se carga datos del estudiante.");


            return new EstudianteMapping().estudianteEntityToEstudianteDTO(crearEstudiante);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Error al crear estudiante debido a un problema de datos al persistir en la base de datos.", e);
            throw new FinalizacionProcesoExcepcion("Ocurrió un error al crear el estudiante debido a un problema de acceso a la base de datos");
        } catch (DataAccessException e) {
            LOGGER.error("Error al crear estudiante debido a un problema de conexion a la base de datos.", e);
            throw new RuntimeException("Ocurrió un error al crear el estudiante debido a un problema de acceso a la base de datos");
        }  catch (Exception e) {
            LOGGER.error("Error al crear estudiante.", e);
            throw new RuntimeException("Ocurrió un error al crear el estudiante");
        }
    }

    @Override
    public EstudianteDto buscarEstudiante(String documento) {
        try {
            EstudianteEntity estudianteEntity = iEstudianteRepository.findByDocumento(documento);
            if (estudianteEntity != null)
                return new EstudianteMapping().estudianteEntityToEstudianteDTO(estudianteEntity);

            LOGGER.warn("No se encontró el estudiante con documento: {}", documento);
            return null;

        } catch (DataAccessException e) {
            LOGGER.error("Error de acceso a la base de datos al buscar estudiante.", e);
            throw new RuntimeException("Error de acceso a la base de datos al buscar el estudiante");
        } catch (Exception e) {
            LOGGER.error("Error al buscar estudiante.", e);
            throw new RuntimeException("Ocurrió un error al buscar el estudiante");
        }
    }

    public void datosMatricula(EstudianteDto estudianteDto) {
        try {
            EstudianteDto estudianteEncontrado = buscarEstudiante(estudianteDto.getDocumento());
            if (estudianteEncontrado != null) {
                estudianteDto.getMatriculaDto().setIdEstudiante(estudianteEncontrado);
                iMatriculaService.crearMatricula(estudianteDto.getMatriculaDto());
            } else {
                LOGGER.warn("No se encontró el estudiante con documento: {}", estudianteDto.getDocumento());
                throw new RuntimeException("No se encontró el estudiante");
            }
        } catch (Exception e) {
            LOGGER.error("Error al procesar datos de matrícula para el estudiante.", e);
            throw new RuntimeException("Ocurrió un error al procesar datos de matrícula para el estudiante");
        }
    }

}
