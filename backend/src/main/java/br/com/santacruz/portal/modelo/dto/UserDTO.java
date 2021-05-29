package br.com.santacruz.portal.modelo.dto;

import br.com.santacruz.portal.modelo.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String token;

    private String login;

    private Date lastAccess;

    public static UserDTO toDTO(User user, String token) {
        return new UserDTO(user.getId(), token, user.getLogin(), user.getLastAccess());
    }

}
