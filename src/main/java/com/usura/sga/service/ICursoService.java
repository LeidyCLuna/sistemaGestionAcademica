package com.usura.sga.service;

import com.usura.sga.dto.CursoDto;

public interface ICursoService {

    CursoDto crearCurso(CursoDto cursoDto);
    CursoDto buscarCurso(Integer idCursoDto);
}
