package fr.eni.ecole.dal;


import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.exception.BusinessException;


public interface RetraitDAO extends CRUD<Retrait> {
	
	
	Retrait selectByArticle(Article a) throws BusinessException;
	void insert(Retrait item) throws BusinessException;
	List<Retrait> selectAll() throws BusinessException;
	Retrait selectById(int id) throws BusinessException;
	void update(Retrait item) throws BusinessException;
	void delete(Retrait item) throws BusinessException;

}
