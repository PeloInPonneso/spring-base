package it.cabsb.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Preconditions;

public abstract class AbstractHibernateDao<T extends Serializable> implements IOperations<T> {
	
	private static Logger _log = LoggerFactory.getLogger(AbstractHibernateDao.class);
	
    private Class<T> type;
    
    private @Value("${hibernate.search.worker.batch_size}") String  batchSize;
    private int BATCH_SIZE;
    

    @PersistenceContext(unitName="jpa-persistence")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractHibernateDao() {
    	Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
	}

    public final void reindex() {
    	try {
        	BATCH_SIZE = Integer.parseInt(batchSize);
        } catch(NumberFormatException e) {
        	_log.warn("NumberFormatException: Reindex Disabled for " + type);
        }
    	if(BATCH_SIZE>0) {
	    	_log.info("Start " + type.getName() + " reindex");
	    	FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
	    	fullTextSession.setFlushMode(FlushMode.MANUAL);
	    	fullTextSession.setCacheMode(CacheMode.IGNORE);
	    	//Scrollable results will avoid loading too many objects in memory
	    	ScrollableResults results = fullTextSession.createCriteria(type)
	    			.setFetchSize(BATCH_SIZE)
	    			.scroll(ScrollMode.FORWARD_ONLY);
	    	int index = 0;
	    	while( results.next() ) {
	    	    index++;
	    	    //Index each element
	    	    fullTextSession.index( results.get(0) );
	    	    if (index % BATCH_SIZE == 0) {
	    	    	//Apply changes to indexes
	    	        fullTextSession.flushToIndexes();
	    	        //Free memory since the queue is processed
	    	        fullTextSession.clear(); 
	    	    }
	    	}
	    	_log.info("End of " + type.getName() + " reindex. Reindexed " + (index-1) + " results");
    	}
    }
    
	@SuppressWarnings("unchecked")
	public final T findOne(final long id) {
        return ((T) getCurrentSession().get(type, id));
    }

	@SuppressWarnings("unchecked")
    public final List<T> findAll() {
        return getCurrentSession().createQuery("from " + type.getName()).list();
    }
	
	public int countAll() {
		return countByCriteria();
	}
	
	public final List<T> findByCriteria(final Criterion... criterions) {
		return findByCriteria(-1, -1, criterions);
	}
	
	public final T findOneByCriteria(final Criterion... criterions) {
		List<T> list = findByCriteria(0, 1, criterions);
		if(list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public final List<T> findByCriteria(final int firstResult, final int maxResults, final Criterion... criterions) {
		Criteria crit = getCurrentSession().createCriteria(type);
		for (Criterion criterion : criterions) {
			crit.add(criterion);
		}
		if (firstResult > 0) {
			crit.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			crit.setMaxResults(maxResults);
		}
		return crit.list();
    }
	
	public int countByCriteria(final Criterion... criterions) {
		Criteria crit = getCurrentSession().createCriteria(type);
		crit.setProjection(Projections.rowCount());
		for (final Criterion criterion : criterions) {
			crit.add(criterion);
		}
		return ((Long) crit.uniqueResult()).intValue();
	}
	
    public final void create(final T entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().persist(entity);
    }

    @SuppressWarnings("unchecked")
    public final T update(final T entity) {
        Preconditions.checkNotNull(entity);
        return (T) getCurrentSession().merge(entity);
    }

    public final void delete(final T entity) {
        Preconditions.checkNotNull(entity);
        getCurrentSession().delete(entity);
    }

    public final void deleteById(final long entityId) {
        final T entity = findOne(entityId);
        Preconditions.checkState(entity != null);
        delete(entity);
    }

    protected final Session getCurrentSession() {
    	return (Session) entityManager.getDelegate();
    }

}
