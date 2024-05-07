package com.usura.sga.service.impl;

import com.google.gson.Gson;
import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.entity.EstudianteEntity;
import com.usura.sga.mapping.EstudianteMapping;
import com.usura.sga.repository.IEstudianteRepository;
import com.usura.sga.service.IEstudianteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EstudianteServiceimpl implements IEstudianteService {

    private final IEstudianteRepository iEstudianteRepository;

    @Override
    public EstudianteDto crearEstudiante(EstudianteDto estudianteDto) {
        EstudianteDto consultaEstudianteDto = buscarEstudiante(estudianteDto.getDocumento());
        if (consultaEstudianteDto == null) {
            EstudianteEntity crearEstudiante = new EstudianteMapping().estudianteDtoToEstudianteEntity(estudianteDto);
            iEstudianteRepository.saveAndFlush(crearEstudiante);
            return new EstudianteMapping().estudianteEntityToEstudianteDTO(crearEstudiante);
        } else {
            return null;
        }
    }

    @Override
    public EstudianteDto buscarEstudiante(String documento) {
        EstudianteEntity estudianteEntity = iEstudianteRepository.findByDocumento(documento);
        if (estudianteEntity != null) {
            return new EstudianteMapping().estudianteEntityToEstudianteDTO(estudianteEntity);
        } else {
            return null;
        }
    }

}