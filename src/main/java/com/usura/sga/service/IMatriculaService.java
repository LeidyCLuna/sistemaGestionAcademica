package com.usura.sga.service;

import com.usura.sga.dto.MatriculaDto;

public interface IMatriculaService {

    MatriculaDto crearMatricula(MatriculaDto matriculaDto);
    MatriculaDto buscarMatricula(Integer idMatriculaDto);
}
