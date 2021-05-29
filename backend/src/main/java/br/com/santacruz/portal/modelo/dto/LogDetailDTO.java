package br.com.santacruz.portal.modelo.dto;

import br.com.santacruz.portal.modelo.Log;
import br.com.santacruz.portal.modelo.LogDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class LogDetailDTO {

    private String description;

    private String information;

    private LocalDate dateOccured;

    private String status;

    public LogDetail toEntity() {
        return new LogDetail(this.description, this.information, this.dateOccured, this.status);
    }

}
