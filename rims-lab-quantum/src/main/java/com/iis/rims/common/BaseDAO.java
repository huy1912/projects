package com.iis.rims.common;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.iis.rims.domain.BaseDomain;
import com.iis.rims.exception.DAOException;

/**
 * Home object for domain model class SystemUser.
 * 
 * @see com.iis.rims.hibernate.dao.SystemUser
 * @author Hibernate Tools
 */
public abstract class BaseDAO<T extends BaseDomain, ID extends Number> {

	private static final Logger log = Logger.getLogger(BaseDAO.class);

	private final SessionFactory sessionFactory = RIMSHibernateUtil
			.getSessionFactory();

	private final Class<?> clazz;

	protected final SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	protected final Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	public BaseDAO() {
		ParameterizedType parameterizedType = (ParameterizedType) this
				.getClass().getGenericSuperclass();

		this.clazz = (Class) parameterizedType.getActualTypeArguments()[0];
	}

	public void persist(T transientInstance) {
		log.debug("persisting instance "
				+ transientInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.persist(transientInstance);
			transaction.commit();
			log.debug("persist successful");
		} catch (Exception re) {
			log.error("persist failed", re);
			transaction.rollback();
			throw re;
		}
	}
	
	public boolean markDeleted(String transientInstanceName, Integer transientInstanceId) {
		return false;
	}
	
	public boolean markDeleted(T transientInstance) {
		return false;
	}

	public ID save(T transientInstance) {
		log.debug("saving instance " + transientInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			ID key = (ID) sessionFactory.getCurrentSession().save(
					transientInstance);
			transaction.commit();
			log.debug("save successful");
			return key;
		} catch (Exception re) {
			log.error("save failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public T saveOrUpdate(T transientInstance) {
		log.debug("saving instance " + transientInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(transientInstance);
			transaction.commit();
			log.debug("save successful");
			return transientInstance;
		} catch (Exception re) {
			log.error("save failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public void update(T transientInstance) {
		log.debug("updating instance " + transientInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			sessionFactory.getCurrentSession().update(transientInstance);
			transaction.commit();
			log.debug("update successful");
		} catch (Exception re) {
			log.error("update failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public void attachDirty(T instance) {
		log.debug("attaching dirty instance " + instance.getClass().getName());
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (Exception re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(T instance) {
		log.debug("attaching clean instance " + instance.getClass().getName());
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (Exception re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(T persistentInstance) {
		log.debug("deleting instance "
				+ persistentInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
			transaction.commit();
		} catch (Exception re) {
			log.error("delete failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public boolean delete(ID[] keys, String fieldName) {
		return delete(Arrays.asList(keys), fieldName);
	}

	public boolean delete(ID key, String fieldName) {
		return this.delete(Arrays.asList(key), fieldName);
	}

	public boolean delete(List<ID> keys, String fieldName) {
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			String joinedKey = StringUtils.join(keys, ",");
			String sqlQuery = String.format("delete from %s where %s IN (%s)",
					clazz.getSimpleName(), fieldName, joinedKey);
			Query namedQuery = session.createQuery(sqlQuery);
			int count = namedQuery.executeUpdate();
			transaction.commit();
			return count > 0;
		} catch (Exception ex) {
			transaction.rollback();
			throw new DAOException(ex);
		}
	}
	
	public boolean executeSoftDelete(Integer lastUpdatedBy, ParameterBuilder parameterBuilder) {
		if (parameterBuilder == null || parameterBuilder.getParameters().isEmpty()) {
			throw new RuntimeException("The input value is invalid.");
		}
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<String> conditions = new ArrayList<String>();
			for (String fieldName : parameterBuilder.getParameters().keySet()) {
				conditions.add(String.format("%s=:%s", fieldName, fieldName));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date now = new Date();
			
			String sqlQuery = String.format("update %s set deleted = true, " +
					"entityStatus = false, lastUpdatedBy = %d, " +
					"lastUpdatedDate = convert(datetime, '%s', 103) where %s",
					clazz.getSimpleName(), lastUpdatedBy, sdf.format(now), StringUtils.join(conditions, " AND "));
			Query namedQuery = session.createQuery(sqlQuery);
			for (Entry<String, Object> entry : parameterBuilder.getParameters().entrySet()) {
				namedQuery.setParameter(entry.getKey(), entry.getValue());
			}
			int count = namedQuery.executeUpdate();
			transaction.commit();
			return count > 0;
		} catch (Exception ex) {
			transaction.rollback();
			throw new DAOException(ex);
		}
	}

	public T merge(T detachedInstance) {
		log.debug("merging instance " + detachedInstance.getClass().getName());
		try {
			T result = (T) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (Exception re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public T findById(ID id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			T instance = (T) session.get(clazz, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("getting instance : " + instance.getClass().getName()
						+ " with id " + id);
				log.debug("get successful, instance found");
			}
			transaction.commit();
			return instance;
		} catch (Exception re) {
			log.error("get failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public List<T> findAllLookup(String... fields) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(clazz);
			ProjectionList projectionList = Projections.projectionList();
			for (String field : fields) {
				projectionList.add(Property.forName(field), field);
			}
			criteria.setProjection(projectionList).setResultTransformer(
					Transformers.aliasToBean(clazz));
			List<T> results = criteria.list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("find by example failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public List<T> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			List<T> results = session.createCriteria(clazz).list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("find by example failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public List<T> findAll(String sortName) {
		return findAll(sortName, SortDirection.ASC);
	}

	public List<T> findAll(String sortName, SortDirection sortDirection) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(clazz);

			if (sortDirection == SortDirection.ASC) {
				criteria.addOrder(org.hibernate.criterion.Order.asc(sortName));
			} else {
				criteria.addOrder(org.hibernate.criterion.Order.desc(sortName));
			}

			List<T> results = criteria.list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("find by example failed", re);
			transaction.rollback();
			throw re;
		}
	}

	protected String getPrimaryColumn() {
		return "id";
	}

	public ID getMaxId() {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(clazz).setProjection(
					Projections.max(getPrimaryColumn()));
			ID id = (ID) criteria.uniqueResult();
			transaction.commit();
			return id;
		} catch (Exception ex) {
			transaction.rollback();
			throw new DAOException(ex);
		}
	}

	public final List<T> findByCriteria(String fieldName, Object fieldValue) {
		return this.findByCriteria(Restrictions.eq(fieldName, fieldValue));
	}

	public List<T> findByCriteria(Criterion... exp) {
		return findByCriteria(Arrays.asList(exp));
	}

	public List<T> findByCriteria(String sortName, SortDirection sortDirection,
			Criterion... exp) {
		return findByCriteria(Arrays.asList(new SortInfo(sortName, sortDirection)), Arrays.asList(exp));
	}

	public List<T> findByCriteria(String sortName, SortDirection sortDirection,
			List<Criterion> exp) {
		return findByCriteria(Arrays.asList(new SortInfo(sortName, sortDirection)), exp);
	}

	public List<T> findByCriteria(List<SortInfo> sortInfos, Criterion... exp) {
		return findByCriteria(sortInfos, Arrays.asList(exp));
	}

	public List<T> findByCriteria(List<Criterion> exp) {
		return findByCriteria((List<SortInfo>) null, exp);
	}

	public List<T> findByCriteria(List<SortInfo> sortInfos, List<Criterion> exp) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(clazz);
			if (exp != null) {
				for (Criterion o : exp) {
					criteria.add(o);
				}
			}

			if (sortInfos != null) {
				for (SortInfo info : sortInfos) {
					// org.hibernate.criterion.Order.
					if (info.getDirection() == SortDirection.ASC) {
						criteria.addOrder(org.hibernate.criterion.Order
								.asc(info.getProperty()));
					} else {
						criteria.addOrder(org.hibernate.criterion.Order
								.desc(info.getProperty()));
					}
				}
			}
			List<T> results = criteria.list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("find by criteria failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public final T findByUnique(String fieldName, Object fieldValue) {
		List<T> list = this.findByCriteria(Restrictions.eq(fieldName,
				fieldValue));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	protected final T findByUnique(Criterion... exp) {
		return findByUnique(Arrays.asList(exp));
	}

	protected final T findByUnique(String sortName,
			SortDirection sortDirection, Criterion... exp) {
		return findByUnique(sortName, sortDirection, Arrays.asList(exp));
	}

	protected final T findByUnique(List<Criterion> exp) {
		return findByUnique(null, null, exp);
	}

	protected final T findByUnique(String sortName,
			SortDirection sortDirection, List<Criterion> exp) {
		List<T> list = null;
		if (sortName == null || sortDirection == null) {
			list = this.findByCriteria(exp);
		} else {
			list = this.findByCriteria(sortName, sortDirection, exp);
		}
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	// Paging
	public int getCount() {
		return getCount(Collections.EMPTY_LIST);
	}

	public int getCount(Criterion exp) {
		return getCount(Arrays.asList(exp));
	}
	
	public int getCount(Criterion... exp) {
		return getCount(Arrays.asList(exp));
	}

	public int getCount(List<Criterion> exp) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(clazz);
			if (exp != null) {
				for (Criterion o : exp) {
					criteria.add(o);
				}
			}
			Long count = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
			transaction.commit();
			return count.intValue();
		} catch (Exception ex) {
			transaction.rollback();
			throw new DAOException(ex);
		}
	}

	public int getUniqueCount(List<Criterion> exp, String distinctBy) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(clazz);
			if (exp != null) {
				for (Criterion o : exp) {
					criteria.add(o);
				}
			}
			//criteria.setProjection(Projections.rowCount());
			criteria.setProjection(Projections.countDistinct(distinctBy));
			Long count = (Long) criteria.uniqueResult();

			transaction.commit();
			if (count != null)
				return count.intValue();
			else return 0;
		} catch (Exception ex) {
//			ex.printStackTrace();
			transaction.rollback();
			throw new DAOException(ex);
		}
	}

	public <T> List<T> getPagingList(String storeProcedureName,
			ParameterBuilder parameterBuilder, Class<T> clazz, String sortName,
			SortDirection sortDirection) {
		return this.getNamedQuery(storeProcedureName, parameterBuilder, clazz,
				sortName, sortDirection);
	}

	public List<T> getPagingList(int startIndex, int pageSize) {
		return getPagingList(startIndex, pageSize, Collections.EMPTY_LIST);
	}

	public List<T> getPagingList(int startIndex, int pageSize, Criterion... exp) {
		return getPagingList(startIndex, pageSize, Arrays.asList(exp));
	}

	public List<T> getPagingList(int startIndex, int pageSize,
			List<Criterion> exp) {
		return getPagingList(startIndex, pageSize, null, null, exp);
	}

	public List<T> getPagingList(int startIndex, int pageSize, String sortName,
			SortDirection sortDirection, List<Criterion> exp) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(clazz);
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
			if (exp != null) {
				for (Criterion o : exp) {
					criteria.add(o);
				}
			}
			if (sortDirection != null && sortName != null) {
				if (sortDirection == SortDirection.ASC) {
					criteria.addOrder(org.hibernate.criterion.Order
							.asc(sortName));
				} else {
					criteria.addOrder(org.hibernate.criterion.Order
							.desc(sortName));
				}
			}

			List<T> results = criteria.list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("getPagingList failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public List<T> getUniquePagingList(int startIndex, int pageSize,
			String sortName, List<String> distinctBy, SortDirection sortDirection,
			List<Criterion> exp) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(clazz);
			criteria.setFirstResult(startIndex);
			criteria.setMaxResults(pageSize);
			ProjectionList prjl = Projections.projectionList();
			/*prjl.add(Projections.distinct(Projections.property("nricFinNumber")));
			prjl.add(Projections.distinct(Projections.property("patientName")));
			prjl.add(Projections.distinct(Projections.property("dateOfBirth")));*/
			// TODO HUY Why the patientId in this common method
			prjl.add(Projections.distinct(Projections.property("patientId")));
			for (String string : distinctBy){
				prjl.add(Property.forName(string),string);
			}
		
			criteria.setProjection(prjl).setResultTransformer(
					Transformers.aliasToBean(clazz));///.setProjection(projection))
			// criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
			if (exp != null) {
				for (Criterion o : exp) {
					criteria.add(o);
				}
			}
			if (sortDirection != null && sortName != null) {
				if (sortDirection == SortDirection.ASC) {
					criteria.addOrder(org.hibernate.criterion.Order
							.asc(sortName));
				} else {
					criteria.addOrder(org.hibernate.criterion.Order
							.desc(sortName));
				}
			}

			List<T> results = criteria.list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("getPagingList failed", re);
			transaction.rollback();
			throw re;
		}
	}

	public <E> List<E> getUniqueList(String fieldName, Criterion... exp) {
		return this.getUniqueList(fieldName, Arrays.asList(exp));
	}
	
	public <E> List<E> getUniqueList(String fieldName, String sortName, 
			SortDirection sortDirection, Criterion... exp) {
		return this.getUniqueList(fieldName, Arrays.asList(exp), sortName, sortDirection);
	}

	public <E> List<E> getUniqueList(String fieldName, List<Criterion> exp) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(clazz);
			criteria.setProjection(Projections.projectionList().add(
					Projections.property(fieldName)));
			if (exp != null) {
				for (Criterion o : exp) {
					criteria.add(o);
				}
			}
			List<E> list = criteria.list();
			transaction.commit();

			return list;
		} catch (Exception re) {
			transaction.rollback();
			throw re;
		}
	}

	public <E> List<E> getUniqueList(String fieldName, List<Criterion> exp, 
			String sortName, SortDirection sortDirection) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(clazz);
			criteria.setProjection(Projections.projectionList().add(
					Projections.property(fieldName)));
			if (exp != null) {
				for (Criterion o : exp) {
					criteria.add(o);
				}
			}
			if (sortDirection == SortDirection.ASC) {
				criteria.addOrder(org.hibernate.criterion.Order.asc(sortName));
			} else {
				criteria.addOrder(org.hibernate.criterion.Order.desc(sortName));
			}
			
			List<E> list = criteria.list();
			transaction.commit();

			return list;
		} catch (Exception re) {
			transaction.rollback();
			throw re;
		}
	}
	
	protected <E> List<E> getNamedQuery(String queryName,
			ParameterBuilder parameterBuilder) {
		return this.getNamedQuery(queryName, parameterBuilder, null);
	}

	protected <E> List<E> getNamedQuery(String queryName,
			ParameterBuilder parameterBuilder, Class<E> clazz) {
		return this.getNamedQuery(queryName, parameterBuilder, clazz, null,
				null);
	}

	protected <E> List<E> getNamedQuery(String queryName,
			ParameterBuilder parameterBuilder, String sortName,
			SortDirection sortDirection) {
		return this.getNamedQuery(queryName, parameterBuilder, null, sortName,
				sortDirection);
	}

	protected <E> List<E> getNamedQuery(String queryName,
			ParameterBuilder parameterBuilder, Class<E> clazz, String sortName, SortDirection sortDirection) {
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query namedQuery = session.getNamedQuery(queryName);
			for (Entry<String, Object> entry : parameterBuilder.getParameters().entrySet()) {
				namedQuery.setParameter(entry.getKey(), entry.getValue());
			}

			if (parameterBuilder.getTimestampParameters() != null) {
				for (Entry<String, Date> entry : parameterBuilder.getTimestampParameters().entrySet()) {
					namedQuery.setTimestamp(entry.getKey(), entry.getValue());
				}
			}
			if (clazz != null) {
				namedQuery.setResultTransformer(Transformers.aliasToBean(clazz));
			}
			if (sortDirection != null && sortName != null) {
				namedQuery.setParameter("sortKey", sortName);
				namedQuery.setParameter("sortDirection", sortDirection.name());
			}
			List<E> list = namedQuery.list();
			transaction.commit();
			return list;
		} catch (Exception ex) {
			transaction.rollback();
			throw new DAOException(ex);
		}
	}

	protected int executeNamedQuery(String queryName,
			ParameterBuilder parameterBuilder) {
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query namedQuery = session.getNamedQuery(queryName);
			// TODO Extract method later.
			for (Entry<String, Object> entry : parameterBuilder.getParameters()
					.entrySet()) {
				namedQuery.setParameter(entry.getKey(), entry.getValue());
			}

			if (parameterBuilder.getTimestampParameters() != null) {
				for (Entry<String, Date> entry : parameterBuilder
						.getTimestampParameters().entrySet()) {
					namedQuery.setTimestamp(entry.getKey(), entry.getValue());
				}
			}
			int updatedRecords = namedQuery.executeUpdate();
			transaction.commit();
			return updatedRecords;
		} catch (Exception ex) {
			transaction.rollback();
			throw new DAOException(ex);
		}
	}

	
	/**
	 * @param queryName
	 * @param parameterBuilder
	 * @param clazz
	 * @return
	 */
	protected <E> E getNamedQueryUnique(String queryName,
			ParameterBuilder parameterBuilder, Class<E> clazz) {
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query namedQuery = session.getNamedQuery(queryName);
			// TODO Need to take care of Date and Timestamp parameter.
			for (Entry<String, Object> entry : parameterBuilder.getParameters()
					.entrySet()) {
				Object value = entry.getValue();
				if (value != null && value.getClass().isArray()) {
					namedQuery.setParameterList(entry.getKey(), (Object[])value);
				}
				else if (value != null && Collection.class.isAssignableFrom(value.getClass())) {
					namedQuery.setParameterList(entry.getKey(), (Collection)value);
				}
				else {
					namedQuery.setParameter(entry.getKey(), value);
				}
			}

			if (parameterBuilder.getTimestampParameters() != null) {
				for (Entry<String, Date> entry : parameterBuilder
						.getTimestampParameters().entrySet()) {
					namedQuery.setTimestamp(entry.getKey(), entry.getValue());
				}
			}
			if (clazz != null) {
				if (clazz != String.class && clazz != Integer.class && clazz != BigDecimal.class 
						&& !BaseDomain.class.isAssignableFrom(clazz)) {
					namedQuery.setResultTransformer(Transformers.aliasToBean(clazz));
				}
			}

			E ret = (E) namedQuery.uniqueResult();
			transaction.commit();
			return ret;
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
			throw new DAOException(ex);
		}
	}

	public int getTotalRows(String queryName, ParameterBuilder parameterBuilder) {
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query namedQuery = session.getNamedQuery(queryName);
			// TODO Need to take care of Date and Timestamp parameter.
			for (Entry<String, Object> entry : parameterBuilder.getParameters()
					.entrySet()) {
				namedQuery.setParameter(entry.getKey(), entry.getValue());
			}

			if (parameterBuilder.getTimestampParameters() != null) {
				for (Entry<String, Date> entry : parameterBuilder
						.getTimestampParameters().entrySet()) {
					namedQuery.setTimestamp(entry.getKey(), entry.getValue());
				}
			}
			Object ret = namedQuery.uniqueResult();
			transaction.commit();
			return ret != null ? ((Integer) ret).intValue() : 0;
		} catch (Exception ex) {
			transaction.rollback();
			throw new DAOException(ex);
		}
	}

	public <E> E getMaxValue(String columnName) {
		return this.getMaxValue(columnName, (List<Criterion>) null);
	}

	public <E> E getMaxValue(String columnName, Criterion... exp) {
		return this.getMaxValue(columnName, Arrays.asList(exp));
	}

	public <E> E getMaxValue(String columnName, List<Criterion> exp) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(clazz).setProjection(
					Projections.max(columnName));
			if (exp != null) {
				for (Criterion criterion : exp) {
					criteria.add(criterion);
				}
			}
			E value = (E) criteria.uniqueResult();
			transaction.commit();
			return value;
		} catch (Exception ex) {
			transaction.rollback();
			throw new DAOException(ex);
		}
	}
	
	// Projection, need to provide the overload if require
	public <E> List<E> findProjectByCriteria(List<Criterion> exp, List<? extends Projection> projections) {
		return findProjectByCriteria(exp, projections, null, null, null);
	}
	
	public <E> List<E> findProjectByCriteria(List<? extends Criterion> exp, List<? extends Projection> projections,
			String sortName, SortDirection sortDirection, Integer maxResults) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(clazz);
			if (exp != null) {
				for (Criterion criterion : exp) {
					criteria.add(criterion);
				}
			}
			if (projections != null) {
				ProjectionList projectionList = Projections.projectionList();
				for (Projection projection : projections) {
					projectionList.add(projection);
				}
				criteria.setProjection(projectionList);
			}
			
			if (sortDirection != null && sortName != null) {
				if (sortDirection == SortDirection.ASC) {
					criteria.addOrder(org.hibernate.criterion.Order.asc(sortName));
				} else {
					criteria.addOrder(org.hibernate.criterion.Order.desc(sortName));
				}
			}
			
			if (maxResults != null) {
				criteria.setMaxResults(maxResults);
			}

			List<E> results = criteria.list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("find by criteria failed", re);
			transaction.rollback();
			throw re;
		}
	}
}
