package br.com.santacruz.portal.fachada;

import br.com.santacruz.portal.modelo.Log;
import br.com.santacruz.portal.modelo.dto.LogDTO;

import java.util.List;

public interface LogService {

    public List<Log> getLogsWithFilter(LogDTO log);

}
