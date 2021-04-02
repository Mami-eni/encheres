package fr.eni.ecole.dal;

import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

public interface ArticleDAO extends CRUD<Article>{

	void insert(Article a) throws BusinessException;
	Article selectById(int id) throws BusinessException;
	List<Article> selectByUser(Utilisateur utilisateur) throws BusinessException;
	
}
