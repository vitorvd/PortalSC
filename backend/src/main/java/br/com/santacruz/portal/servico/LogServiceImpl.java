package br.com.santacruz.portal.servico;

import br.com.santacruz.portal.dao.LogDAO;
import br.com.santacruz.portal.fachada.LogService;
import br.com.santacruz.portal.modelo.Log;
import br.com.santacruz.portal.modelo.dto.LogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService{

    @Autowired
    private LogDAO dao;

    @Override
    public List<Log> getLogsWithFilter(LogDTO dto) {
        return this.dao.findAll((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(dto.getProcess() != null)
                predicates.add(builder.like(builder.lower(root.get("process")), dto.getProcess().toLowerCase()));

            if(dto.getStatus() != null)
                predicates.add(builder.like(builder.lower(root.get("status")), dto.getStatus().toLowerCase()));

            if(dto.getUserLogin() != null)
                predicates.add(builder.like(builder.lower(root.get("userLogin")), dto.getUserLogin().toLowerCase()));

            if(dto.getStartDate() != null)
                predicates.add(builder.greaterThanOrEqualTo(root.<LocalDate>get("startDate"), dto.getStartDate()));

            if(dto.getEndDate() != null)
                predicates.add(builder.lessThanOrEqualTo(root.<LocalDate>get("endDate"), dto.getEndDate()));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

}
