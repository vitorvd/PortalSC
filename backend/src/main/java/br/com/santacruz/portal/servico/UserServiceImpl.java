package br.com.santacruz.portal.servico;

import br.com.santacruz.portal.dao.UserDAO;
import br.com.santacruz.portal.fachada.UserService;
import br.com.santacruz.portal.modelo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO dao;

    @Override
    public User autenticate(final String login, final String password) {

        try {
            final User user = dao.findByLogin(login);

            if (BCrypt.checkpw(password, user.getPasswordHash())) {
                return user;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return null;
    }

    @Transactional
    @Override
    public void setLastAccess(User user) {
        this.dao.setLastAccess(user.getId(), new Date());
    }

    @Transactional
    @Override
    public void save(User user) {
        this.dao.save(user);
    }
}
