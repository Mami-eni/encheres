/**
 * 
 */
package fr.eni.ecole.tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import fr.eni.ecole.bo.Article;
import fr.eni.ecole.bo.Categorie;
import fr.eni.ecole.bo.Utilisateur;
import fr.eni.ecole.dal.ArticleDAO;
import fr.eni.ecole.dal.CategorieDAO;
import fr.eni.ecole.dal.Connect;
import fr.eni.ecole.dal.DAOFactory;
import fr.eni.ecole.dal.UtilisateurDAO;
import fr.eni.ecole.exception.BusinessException;

/**
 * @author sun
 *
 */
public class ArticleJDBCTest {
	Article article;
	ArticleDAO daoA;
	List<Article> articles;

	Categorie categorie;
	CategorieDAO daoC;
	Utilisateur utilisateur;
	UtilisateurDAO daoU;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Connect.DEV_MODE = false;
		
//		utilisateur = new Utilisateur();
//		utilisateur.setAdministrateur(false);
//		utilisateur.setCodePostal("00000");
//		utilisateur.setCredit(0);
//		utilisateur.setEmail("asda@asdas.com");
//		utilisateur.setMotDePasse("mdp");
//		utilisateur.setNom("null");
//		utilisateur.setTelephone("012345678");
//		utilisateur.setPrenom("pointer");
//		utilisateur.setPseudo("abcd");
//		utilisateur.setRue("12 av Paris");
//		utilisateur.setVille("Rennes");
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		Connect.DEV_MODE = true;

	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.ArticleJDBC#insert(fr.eni.ecole.bo.Article)}.
	 */
	@Test
	public void testInsert() {
		
		try {
			daoC = DAOFactory.getCategorieDAO();
			categorie = daoC.selectById(1);
			
			daoU = DAOFactory.getUtilisateurDAO();
			utilisateur = daoU.selectById(4);
			
			daoA = DAOFactory.getArticleDAO();
			articles = daoA.selectAll();
			
			article = new Article();
			article.setCategorie(categorie);
			article.setDateDebutEncheres(LocalDate.of(2020,12,1));
			article.setDateFinEncheres(LocalDate.now());
			article.setDescription("table basse");
//			article.setEtatVente("EC");
			article.setNom("table");
			article.setPrixInitial(12);
			article.setPrixVente(0);
			article.setUtilisateur(utilisateur);
			
			int tailleAvant = articles.size();
			daoA.insert(article);
			List<Article> articlesApres = daoA.selectAll();
			int tailleApres = articlesApres.size();
			assertEquals(1, tailleApres - tailleAvant);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.ArticleJDBC#selectAll()}.
	 */
	@Test
	public void testSelectAll() {
//		int tailleAvant = articles.size();
//		try {
//			daoA.insert(article);
//			List<Article> articlesApres = daoA.selectAll();
//			int tailleApres = articles.size();
//			assertEquals(1, tailleApres - tailleAvant);
//		} catch (BusinessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.ArticleJDBC#selectById(int)}.
	 */
	@Test
	public void testSelectById() {
//		Article art = articles.get(0);
//		try {
//			Article artTest = daoA.selectById(art.getNumero());
//			assertEquals(art, artTest);
//
//		} catch (BusinessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.ArticleJDBC#update(fr.eni.ecole.bo.Article)}.
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.ArticleJDBC#delete(fr.eni.ecole.bo.Article)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.ArticleJDBC#selectByutilisateur(fr.eni.ecole.bo.Utilisateur)}.
	 */
	@Test
	public void testSelectByutilisateur() {
//		Article art = articles.get(0);
//		try {
//			List<Article> artTest = daoA.selectByUser(art.getUtilisateur());
//			assertEquals(art, artTest);
//
//		} catch (BusinessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * Test method for {@link fr.eni.ecole.dal.ArticleJDBC#selectByFiltre(java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String[], int)}.
	 */
	@Test
	public void testSelectByFiltre() {
		fail("Not yet implemented");
	}

}
