package br.com.santacruz.portal.dao;

import br.com.santacruz.portal.fachada.dao.DAO;
import br.com.santacruz.portal.modelo.Log;
import br.com.santacruz.portal.modelo.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LogDAO extends DAO<Long, Log> {

    //public List<Log> findAllByProcessContainsAndStatusEqualsAndUserLoginEqualsAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String process, String status, String userLogin, LocalDate startDate, LocalDate endDate);

}
