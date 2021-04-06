package fr.eni.ecole.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.dal.ArticleDAO;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.exception.BusinessException;
import fr.eni.ecole.exception.Errors;
import fr.eni.ecole.util.Constants;

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
		else if(!a.getDateDebutEncheres().toString().matches(Constants.REGEX_DATE)) {
			throwError = true;
			error.addError(Errors.REGLE_DATE_MAX);
		}
		else if(!a.getDateFinEncheres().toString().matches(Constants.REGEX_DATE)) {
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
		
		List<Article>listeArticles = new ArrayList<Article>();
		boolean selectVenteClose= false;
		// si clique sur bouton 
		
		if(!(null==filtreCheckboxAchat))
		{
			for (String choixFiltreAchat : filtreCheckboxAchat)
			{
				if("enchereObtenues".equalsIgnoreCase(choixFiltreAchat))
				{
					selectVenteClose= true;
					
				}
			}
		}
		
			
			if(selectVenteClose)
			{
				listeArticles = selectAchatRemporte(filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxVente, filtreCheckboxAchat, userId);
			}
			
			else
			{
				listeArticles = article.selectByFiltre(filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxVente, filtreCheckboxAchat, userId);
			}
		
		
		
		return listeArticles;
	}
	
	
	public List<Article> selectAchatRemporte(String filtreTexte, String filtreCategorie, String filtreRadio, String[] filtreCheckboxVente,String[] filtreCheckboxAchat, int userId) throws BusinessException
	{
		
	// appeler selectbyfiltre
		
//		List<Article> listeArticles = DAOFactory.getArticleDAO().selectAchatClos(userId);
		List<Article> listeArticles = article.selectByFiltre(filtreTexte, filtreCategorie, filtreRadio, filtreCheckboxVente, filtreCheckboxAchat, userId);
		List<Article> listeArticlesRemporte= new ArrayList<Article>();
		
		for (Article article : listeArticles)
		{

			List<Enchere> listeEncheres = DAOFactory.getEnchereDAO().selectByArticle(article);
			int montantMax = 0;
			Enchere ench = new Enchere();
			if(!listeEncheres.isEmpty())
			{
				for(Enchere e : listeEncheres) 
				{
					if(e.getMontant() > montantMax)
					{
						montantMax = e.getMontant();
						ench = e;
					}

				}

			}
				
			// est ce que l'enchère est gagnée si oui ajout de cet article aux articles remportés
			
			if(ench.getUtilisateur().getNumero()==userId)
			{
				
				listeArticlesRemporte.add(article);
				

			}
		
				
		}
		return listeArticlesRemporte;
		
	}
}
