package br.com.santacruz.portal.modelo.dto;

import br.com.santacruz.portal.modelo.Log;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
public class LogDTO {

    private String process;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private String userLogin;

}
