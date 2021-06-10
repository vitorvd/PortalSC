package br.com.santacruz.portal.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConvertUtil {

    public static LocalDateTime toLocalDateTime(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.parse(str, formatter);
    }

    public static String toString(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

}
