package fr.eni.ecole.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Retrait;
import fr.eni.ecole.dal.ArticleDAO;
import fr.eni.ecole.dal.Connect;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.dal.RetraitDAO;
import fr.eni.ecole.exception.BusinessException;

public class testRetraitJDBC {

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
		RetraitDAO retrait = DAOFactory.getRetraitDAO();
		Article art = new Article();
		art.setNumero(7357);
		Retrait ret = new Retrait();
		ret.setArticle(art);
		ret.setCodePostal("55420");
		ret.setRue("rue des tests");
		ret.setVille("Junit");
		retrait.insert(ret);
	}

	@Test
	public void testSelectByArticle() throws BusinessException {
		RetraitDAO retrait = DAOFactory.getRetraitDAO();
		ArticleDAO article = DAOFactory.getArticleDAO();
		Article art = article.selectById(36);
		Retrait ret = retrait.selectByArticle(art);
	}

	@Test
	public void testUpdate() throws BusinessException {
		RetraitDAO retrait = DAOFactory.getRetraitDAO();
		ArticleDAO article = DAOFactory.getArticleDAO();
		Article art = article.selectById(36);
		Retrait ret = retrait.selectByArticle(art);
		retrait.update(ret);
	}

	@Test
	public void testDelete() throws BusinessException {
		RetraitDAO retrait = DAOFactory.getRetraitDAO();
		ArticleDAO article = DAOFactory.getArticleDAO();
		Article art = article.selectById(31);
		Retrait ret = retrait.selectByArticle(art);
		retrait.delete(ret);
	}

}
