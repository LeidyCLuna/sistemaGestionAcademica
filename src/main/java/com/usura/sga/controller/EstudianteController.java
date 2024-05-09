package com.usura.sga.controller;

import com.usura.sga.dto.EstudianteDto;
import com.usura.sga.service.IEstudianteService;
import com.usura.sga.util.EstudianteResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/sga")
public class EstudianteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstudianteController.class);
    private final IEstudianteService iEstudianteService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok. se guardo corectamente el estudiante", response = EstudianteDto.class),
            @ApiResponse(code = 400, message = "no llenaste los datos correctamente", response = String.class),
            @ApiResponse(code = 500, message = "error inesperado del sistema")
    })

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstudianteResponse> guardarEsudiante(@RequestBody @Validated EstudianteDto estudianteDto) {
        EstudianteDto estudianteResponse = iEstudianteService.crearEstudiante(estudianteDto);
        try {
            if (estudianteResponse != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new EstudianteResponse("Estudiante registrado exitosamente", estudianteResponse));
            } else
                return ResponseEntity.status(HttpStatus.OK).body(new EstudianteResponse("El estudiante ya se encuentra registrado", null));
        } catch (Exception e) {
            LOGGER.error("Error al guardar estudiante.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EstudianteResponse("Error interno del servidor al procesar la solicitud", null));
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok. se guardo corectamente el elemento", response = EstudianteDto.class),
            @ApiResponse(code = 400, message = "no llenaste los datos correctamente", response = String.class),
            @ApiResponse(code = 500, message = "error inesperado del sistema")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstudianteDto> consultarEstudiante(@RequestParam("documento") String documento) {
        try {
            EstudianteDto estudianteDto = iEstudianteService.buscarEstudiante(documento);
            if (estudianteDto != null) {
                return ResponseEntity.status(HttpStatus.OK).body(estudianteDto);
            } else
                return null;
        } catch (Exception e) {
            LOGGER.error("Error al consultar estudiante.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}