package br.com.santacruz.portal.modelo.dto;

import br.com.santacruz.portal.modelo.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class UserQueryDTO {

    private String login;

    private String perfil;

    private String status;

    private String createdAt;

    private String untilAt;

    public static UserQueryDTO toDTO(User user){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        return new UserQueryDTO(user.getLogin(),
                user.getPerfilType().name(), user.getStatus().name(),
                user.getCreatedAt().format(formatter), user.getUpdatedAt().format(formatter));
    }

}
