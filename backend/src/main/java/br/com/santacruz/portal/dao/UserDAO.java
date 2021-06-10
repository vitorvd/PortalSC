package br.com.santacruz.portal.dao;

import br.com.santacruz.portal.fachada.dao.DAO;
import br.com.santacruz.portal.modelo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface UserDAO extends DAO<Long, User> {

//  @Query("SELECT * FROM tb_users WHERE login=?1")
    public User findByLogin(final String login);

//    @Modifying
//    @Query("UPDATE User u SET u.updatedAt = ?2 WHERE u.id = ?1")
//    public void setUpdatedAt(Long id, LocalDateTime updatedAt);

}
