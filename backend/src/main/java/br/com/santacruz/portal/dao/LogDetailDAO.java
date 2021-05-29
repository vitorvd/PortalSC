package br.com.santacruz.portal.dao;

import br.com.santacruz.portal.fachada.dao.DAO;
import br.com.santacruz.portal.modelo.LogDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogDetailDAO extends DAO<Long, LogDetail> {

    public List<LogDetail> findAllByLog_Id(long logId);

}
