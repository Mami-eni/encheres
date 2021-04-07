package fr.eni.ecole.dal;

import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;

public interface ArticleDAO extends CRUD<Article>{

	List<Article> selectAll() throws BusinessException;
	Article selectById(int id) throws BusinessException;
	List<Article> selectByUser(Utilisateur utilisateur) throws BusinessException;
	public List<Article> selectByFiltre (String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxVente,String[] filtreCheckboxAchat, int userId) throws BusinessException;
	
}
