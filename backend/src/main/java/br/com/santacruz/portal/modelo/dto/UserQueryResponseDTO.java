package br.com.santacruz.portal.modelo.dto;

import br.com.santacruz.portal.utils.DateConvertUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
public class UserQueryResponseDTO {

    private long id;

    private String login;

    private String email;

    private String perfil;

    private String status;

    private String company;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public String getCreatedAtFormatted() {
        return DateConvertUtil.toString(this.createdAt);
    }

    public String getUpdatedAtFormatted() {
        return DateConvertUtil.toString(this.updatedAt);
    }

}
