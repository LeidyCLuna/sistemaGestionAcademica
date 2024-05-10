package com.usura.sga.util;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Utilidades {
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

    public static <T> T conversorJsonToObjeto(String json, Class<T> clase) {
        Gson gson = new Gson();
        return gson.fromJson(json, clase);
    }

    public static boolean objetoNuloVacio(Object str) {
        return str == null || str.toString().isEmpty();
    }

    public static boolean strNuloVacio(String... strings) {
        boolean empty = false;
        for (String str : strings) {
            empty = empty || objetoNuloVacio(str);
        }
        return empty;
    }

    public static boolean strNuloVacioEspacioComoVacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
