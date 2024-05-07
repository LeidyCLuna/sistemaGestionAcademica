package com.usura.sga.service;

import com.usura.sga.dto.EstudianteDto;

public interface IEstudianteService {

    EstudianteDto crearEstudiante(EstudianteDto estudianteDto);
    EstudianteDto buscarEstudiante(String documento);
}
