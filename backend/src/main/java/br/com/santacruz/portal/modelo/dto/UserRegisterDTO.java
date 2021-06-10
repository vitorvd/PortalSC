package br.com.santacruz.portal.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisterDTO {

    private String login;

    private String email;

    private String perfilType;

    private String status;

    private String company;

}
