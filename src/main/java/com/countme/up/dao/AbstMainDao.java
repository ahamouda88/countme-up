package com.countme.up.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * An interface that defines the standard and generic operations to be performed on an entity
 * 
 * @param <T>
 *            the entity's data type
 * @param <E>
 *            the id's data type
 */
@Repository
public abstract class AbstMainDao<T, E extends Serializable> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstMainDao.class);

	@PersistenceContext
	protected EntityManager entityManager;
	protected Class<T> classType;

	public AbstMainDao(Class<T> classType) {
		this.classType = classType;
	}

	/**
	 * A method that creates/saves an entity, given the entity need to be saved
	 * 
	 * @param entity
	 *            the entity to be created/saved
	 * @return true if the given entity is created successfully, otherwise it will return false
	 */
	public boolean save(T entity) {
		try {
			entityManager.persist(entity);
		} catch (Exception ex) {
			LOGGER.error("Failed to persist the following entity: " + entity, ex);
			return false;
		}
		return true;
	}

	/**
	 * A method that updates an entity, given the updated entity
	 * 
	 * @param entity
	 *            the updated entity
	 * @return true if the given entity is updated successfully, otherwise it will return false
	 */
	public boolean update(T entity) {
		try {
			entityManager.merge(entity);
		} catch (Exception ex) {
			LOGGER.error("Failed to update the following entity: " + entity, ex);
			return false;
		}
		return true;
	}

	/**
	 * A method that removes an entity, given the entity to be removed
	 * 
	 * @param entity
	 *            the entity to be removed
	 * @return the removed entity, if the given entity is removed successfully, otherwise it will return <b>null</b>
	 */
	public T remove(T entity) {
		try {
			entityManager.remove(entity);
		} catch (Exception ex) {
			LOGGER.error("Failed to remove the following entity: " + entity, ex);
			return null;
		}
		return entity;
	}

	/**
	 * A method that returns a list of saved entities
	 * 
	 * @return list of entities if the selection is successful, otherwise it will return <b>null</b>
	 */
	public List<T> findAll() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(classType);
			Root<T> from = cq.from(classType);
			CriteriaQuery<T> all = cq.select(from);
			TypedQuery<T> allQuery = entityManager.createQuery(all);
			return allQuery.getResultList();
		} catch (Exception ex) {
			LOGGER.error("Failed to get all entities!", ex);
			return null;
		}
	}

	/**
	 * A method that finds an entity, given the entity's id
	 * 
	 * @param id
	 *            the entity's id
	 * @return the entity mapped to the given id, or <b>null</b> if entity doesn't exist, or id is invalid
	 */
	public T findById(E id) {
		try {
			return entityManager.find(classType, id);
		} catch (Exception ex) {
			LOGGER.error("Failed to find entity wiht the following id: " + id, ex);
			return null;
		}
	}
}
