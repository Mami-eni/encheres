package fr.eni.ecole.dal;

import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

public interface EnchereDAO extends CRUD<Enchere> {

	List<Enchere> selectByArticle(Article a) throws BusinessException;
	List<Enchere> selectByUser(Utilisateur util) throws BusinessException;
	void insert(Enchere item) throws BusinessException;
	List<Enchere> selectAll() throws BusinessException;
	Enchere selectById(int id) throws BusinessException;
	void update(Enchere item) throws BusinessException;
	void delete(Enchere item) throws BusinessException;
	public List<Enchere> selectByFiltre(String filtreTexte, String filtreCategorie) throws BusinessException;

}
