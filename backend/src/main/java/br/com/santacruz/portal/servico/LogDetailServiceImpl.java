package br.com.santacruz.portal.servico;

import br.com.santacruz.portal.dao.LogDetailDAO;
import br.com.santacruz.portal.fachada.LogDetailService;
import br.com.santacruz.portal.modelo.LogDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogDetailServiceImpl implements LogDetailService {

    @Autowired
    private LogDetailDAO dao;

    @Override
    public List<LogDetail> getLogDetailsByLogId(long logId) {
        return this.dao.findAllByLog_Id(logId);
    }
}
