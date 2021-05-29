package br.com.santacruz.portal.fachada;

import br.com.santacruz.portal.modelo.User;

import java.util.Date;

public interface UserService {

    public User autenticate(String login, String password);

    public void setLastAccess(User user);

    public void save(User user);

}
