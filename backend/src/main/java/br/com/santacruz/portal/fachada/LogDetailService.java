package br.com.santacruz.portal.fachada;

import br.com.santacruz.portal.modelo.LogDetail;

import java.util.List;

public interface LogDetailService {

    public List<LogDetail> getLogDetailsByLogId(long logId);

}
