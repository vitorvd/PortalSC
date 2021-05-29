package br.com.santacruz.portal.fachada.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface DAOConsultar<ID, E> extends JpaSpecificationExecutor<E>, QueryByExampleExecutor<E>, PagingAndSortingRepository<E, ID> {

}
