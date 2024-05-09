package com.usura.sga.util;

import com.usura.sga.dto.EstudianteDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteResponse {
    private String mensaje;
    private EstudianteDto estudianteDto;
}

