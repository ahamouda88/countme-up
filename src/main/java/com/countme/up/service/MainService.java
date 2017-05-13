package com.countme.up.service;

import java.io.Serializable;
import java.util.List;

import com.countme.up.dao.AbstMainDao;

/**
 * An interface that defines the business logic for performing main/basic operations on a model, the classes that will
 * implement this interface should have an instance of an implementation of the {@link AbstMainDao}
 * 
 * @author ahamouda
 * 
 */
public interface MainService<T, E extends Serializable> {

	/**
	 * A method that adds a model
	 * 
	 * @param model
	 *            the model need to be added
	 * @return true if model is added successfully, otherwise return false
	 */
	public abstract boolean add(T model);

	/**
	 * A method that deletes a model
	 * 
	 * @param model
	 *            the model need to be deleted
	 * @return the deleted model or <i><b>null</b></i> if model doesn't exist
	 */
	public abstract T delete(T model);

	/**
	 * A method that deletes a model given the model's key
	 * 
	 * @param key
	 *            the model's key that need to be deleted
	 * @return the deleted model or <i><b>null</b></i> if model with the given key doesn't exist
	 */
	public abstract T deleteByKey(E key);

	/**
	 * A method that updates a model
	 * 
	 * @param model
	 *            the model need to be updated
	 * @return true if model is updated successfully, otherwise return false
	 */
	public abstract boolean update(T model);

	/**
	 * A method that returns a model based on the given model's key
	 * 
	 * @param key
	 *            the model's key that need to be retrieved
	 * @return the model or <i><b>null</b></i> if model doesn't exist
	 */
	public abstract T get(E key);

	/**
	 * A method that returns a list of all models
	 * 
	 * @return a list of models
	 */
	public abstract List<T> getAll();
}
