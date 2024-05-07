package com.usura.sga.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.service.IEstudianteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/sga")
public class EstudianteController {

    private final IEstudianteService iEstudianteService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok. se guardo corectamente el estudiante", response = EstudianteDto.class),
            @ApiResponse(code = 400, message = "no llenaste los datos correctamente", response = String.class),
            @ApiResponse(code = 500, message = "error inesperado del sistema")
    })

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> guardarEsudiante(@RequestBody @Validated EstudianteDto estudianteDto) {
        EstudianteDto estudianteResponse = iEstudianteService.crearEstudiante(estudianteDto);
        try {
            if (estudianteResponse != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(estudianteResponse));
            }else
                return ResponseEntity.status(HttpStatus.OK).body("El estudiante ya se encuentra registrado");

        } catch(JsonProcessingException e){
                throw new RuntimeException(e);
            }
        }
    }
