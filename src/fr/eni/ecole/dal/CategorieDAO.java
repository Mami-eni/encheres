package fr.eni.ecole.dal;

import java.util.List;

import fr.eni.ecole.bo.Categorie;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.exception.BusinessException;

public interface CategorieDAO extends CRUD<Categorie> {
	
	List<Categorie> selectAll() throws BusinessException;
	Categorie selectByName(String nom) throws BusinessException;
	Categorie selectById(int id) throws BusinessException;
	void insert(Categorie item) throws BusinessException;
	void update(Categorie item) throws BusinessException;
	void delete(Categorie item) throws BusinessException;
}
