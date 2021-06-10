package br.com.santacruz.portal.servico;

import br.com.santacruz.portal.dao.UserDAO;
import br.com.santacruz.portal.enums.PerfilType;
import br.com.santacruz.portal.enums.Status;
import br.com.santacruz.portal.fachada.UserService;
import br.com.santacruz.portal.modelo.User;
import br.com.santacruz.portal.modelo.dto.UserQueryDTO;
import br.com.santacruz.portal.modelo.dto.UserRegisterDTO;
import br.com.santacruz.portal.utils.DateConvertUtil;
import br.com.santacruz.portal.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<User> getAllUserByFilter(UserQueryDTO dto){
        Specification<User> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(dto.getLogin() != null)
                predicates.add(builder.like(builder.lower(root.get("login")), dto.getLogin().toLowerCase()));

            if(dto.getPerfil() != null)
                predicates.add(builder.equal(root.get("perfilType"), PerfilType.valueOf(dto.getPerfil())));

            if(dto.getStatus() != null)
                predicates.add(builder.equal(root.get("status"), Status.valueOf(dto.getStatus())));

            if(dto.getCreatedAt() != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), DateConvertUtil.toLocalDateTime(dto.getCreatedAt())));

            if(dto.getUntilAt() != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), DateConvertUtil.toLocalDateTime(dto.getUntilAt())));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        //Page<User> list = this.dao.findAll(specification, pageable);

        List<User> list = this.dao.findAll(specification);

        return list;
    }

    @Transactional
    @Override
    public boolean save(User user) {
        User findOne = this.dao.findByLogin(user.getLogin());
        if(findOne != null) return false;

        this.dao.save(user);
        EmailUtil.sendMail(user);
        return true;
    }

    @Transactional
    @Override
    public boolean update(Long id, UserRegisterDTO dto) {
        Optional<User> userOptional = this.dao.findById(id);
        if(!userOptional.isPresent()) return false;

        User userToUpdate = userOptional.get();
        userToUpdate.setLogin(dto.getLogin());
        userToUpdate.setEmail(dto.getEmail());
        userToUpdate.setPerfilType(PerfilType.valueOf(dto.getPerfilType()));
        userToUpdate.setStatus(Status.valueOf(dto.getStatus()));
        userToUpdate.setCompany(dto.getCompany());
        userToUpdate.setUpdatedAt(LocalDateTime.now());

        this.dao.save(userToUpdate);
        return true;
    }

    @Transactional
    @Override
    public Optional<User> delete(Long id) {
        Optional<User> user = this.dao.findById(id);
        if(!user.isPresent()) return user;

        this.dao.delete(user.get());
        return user;
    }

    @Transactional
    @Override
    public Optional<User> findUser(Long id){
        return this.dao.findById(id);
    }

}
