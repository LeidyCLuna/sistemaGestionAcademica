package com.usura.sga.service;

import com.usura.sga.dto.ProgramaDto;

public interface IProgramaService {

    ProgramaDto crearPrograma(ProgramaDto programaDto);
    ProgramaDto buscarPrograma(Integer idProgramaDto);
}
