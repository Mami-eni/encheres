package fr.eni.ecole.dal;

import java.util.List;

import fr.eni.ecole.exception.BusinessException;

public interface CRUD<T> 
{
	void insert(T item) throws BusinessException ;
	List<T> selectAll() throws BusinessException;
	T selectById(int id) throws BusinessException;
	void update(T item) throws BusinessException;
	void delete(T item) throws BusinessException;
}
