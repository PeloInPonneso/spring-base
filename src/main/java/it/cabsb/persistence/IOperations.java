package it.cabsb.persistence;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface IOperations<T extends Serializable> {

	void reindex();

	T findOne(final long id);

    List<T> findAll();
    
    int countAll();
    
    List<T> findByCriteria(final Criterion... criterions);
	
	List<T> findByCriteria(final int firstResult, final int maxResults, final Criterion... criterions);
	
	int countByCriteria(final Criterion... criterions);
    
    void create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final long entityId);

}
