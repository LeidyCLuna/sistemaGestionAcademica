package com.usura.sga.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class FechasUtil {
    public LocalDate dateStringToLocalDate(String fecha){
        if (Objects.nonNull(fecha)) {
            return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return null;
    }

    public String LocalDateToStringDate(LocalDate fecha){
        if (Objects.nonNull(fecha)){
            return  fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return null;
    }
}
