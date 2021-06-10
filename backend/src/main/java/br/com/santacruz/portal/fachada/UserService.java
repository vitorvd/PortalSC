package br.com.santacruz.portal.fachada;

import br.com.santacruz.portal.modelo.User;
import br.com.santacruz.portal.modelo.dto.UserQueryDTO;
import br.com.santacruz.portal.modelo.dto.UserRegisterDTO;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public boolean save(User user);

    public boolean update(Long id, UserRegisterDTO dto);

    public Optional<User> delete(Long id);

    public List<User> getAllUserByFilter(UserQueryDTO dto);

    public User autenticate(String login, String password);

    public Optional<User> findUser(Long id);

    //public List<User> getAllUserByFilter(UserQueryDTO dto, Pageable pageable);

}
