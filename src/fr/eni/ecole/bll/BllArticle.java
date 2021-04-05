package fr.eni.ecole.bll;

import java.time.LocalDate;
import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.dal.ArticleDAO;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.exception.Errors;

public class BllArticle {
	
	private static BllArticle instance;
	private static ArticleDAO article;
	
	private BllArticle() {
		article = DAOFactory.getArticleDAO();
	}
	
	public static BllArticle getBllArticle() {
		if(instance == null) {
			instance = new BllArticle();
		}
		return instance;
	}
	
	public void insert(Article a) throws BusinessException {
		boolean throwError = false;
		BusinessException error = new BusinessException();
		if(a.getNom().length() > 30) {
			throwError = true;
			error.addError(Errors.REGLE_ARTICLE);	
		}
		else if(a.getDescription().length() > 300) {
			throwError = true;
			error.addError(Errors.REGLE_DESCRIPTION);
		}
		else if(!a.getDateDebutEncheres().isAfter(LocalDate.now().minusDays(1))) {
			throwError = true;
			error.addError(Errors.REGLE_DATE);
		}
		else if(!a.getDateFinEncheres().isAfter(LocalDate.now().minusDays(1))) {
			throwError = true;
			error.addError(Errors.REGLE_DATE);
		}
		else if(!a.getDateDebutEncheres().toString().matches("^(202[1-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")) {
			throwError = true;
			error.addError(Errors.REGLE_DATE_MAX);
		}
		else if(!a.getDateFinEncheres().toString().matches("^(202[1-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")) {
			throwError = true;
			error.addError(Errors.REGLE_DATE_MAX);
		}
		if(!throwError) {	
		article.insert(a);
		}else {
			throw error;
		}
	}
	
	public void update(Article a) throws BusinessException {
		article.update(a);
	}
	
	public void delete(Article a) throws BusinessException {
		article.delete(a);
	}
	
	public Article selectById(int id) throws BusinessException {
		return article.selectById(id);
	}
	
	public List<Article> selectByUser(Utilisateur utilisateur) throws BusinessException{
		return article.selectByUser(utilisateur);
	}
	
	public List<Article> selectAll() throws BusinessException {
		return article.selectAll();
	}
	
	public List<Article> selectByFiltre (String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxVente,String[] filtreCheckboxAchat, int userId) throws BusinessException{
		return article.selectByFiltre(filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxVente, filtreCheckboxAchat, userId);
	}
}
