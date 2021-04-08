package fr.eni.ecole.dal;

import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.exception.BusinessException;
/**
 * interface des composants d'accès aux données de la table articles - définit les méthodes implémentées en plus de celles de l'interface générique
 */
public interface ArticleDAO extends CRUD<Article>{

	List<Article> selectByUser(Utilisateur utilisateur) throws BusinessException;
	public List<Article> selectByFiltre (String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxVente,String[] filtreCheckboxAchat, int userId) throws BusinessException;
	void setPrixVente(Article art) throws BusinessException;
	
	
}
