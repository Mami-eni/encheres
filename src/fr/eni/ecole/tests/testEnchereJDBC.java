package fr.eni.ecole.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Enchere;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.dal.ArticleDAO;
import fr.eni.ecole.dal.Connect;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.dal.EnchereDAO;
import fr.eni.ecole.dal.UtilisateurDAO;
import fr.eni.ecole.exception.BusinessException;

public class testEnchereJDBC {

	@Before
	public void setUp() throws Exception {
		Connect.DEV_MODE = false;
	}

	@After
	public void tearDown() throws Exception {
		Connect.DEV_MODE = true;
	}

	@Test
	public void testInsert() throws BusinessException {
		EnchereDAO enchere = DAOFactory.getEnchereDAO();
		Enchere ench = enchere.selectById(4);
		enchere.insert(ench);
	}

	@Test
	public void testSelectByArticle() throws BusinessException {
		ArticleDAO article = DAOFactory.getArticleDAO();
		EnchereDAO enchere = DAOFactory.getEnchereDAO();
		Article art = article.selectById(34);
		List<Enchere> liste = enchere.selectByArticle(art);
	}

	@Test
	public void testSelectByUser() throws BusinessException {
		UtilisateurDAO utilisateur = DAOFactory.getUtilisateurDAO();
		EnchereDAO enchere = DAOFactory.getEnchereDAO();
		Utilisateur util = utilisateur.selectById(2);
		List<Enchere> liste = enchere.selectByUser(util);
	}

	@Test
	public void testSelectAll() throws BusinessException {
		EnchereDAO enchere = DAOFactory.getEnchereDAO();
		List<Enchere> liste = enchere.selectAll();
	}

}
